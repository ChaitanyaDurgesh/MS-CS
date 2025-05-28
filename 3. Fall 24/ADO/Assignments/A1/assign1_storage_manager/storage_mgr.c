#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "storage_mgr.h"

// Initialize the storage manager
void initStorageManager(void) {
    printf("Storage manager initialized.\n");
}

// Create a new page file
RC createPageFile(char *fileName) {
    FILE *file = fopen(fileName, "wb+");
    if (file == NULL) {
        return RC_FILE_NOT_FOUND;
    }

    // Create an empty page
    char *emptyPage = (char *)calloc(PAGE_SIZE, sizeof(char));
    if (emptyPage == NULL) {
        fclose(file);
        return RC_WRITE_FAILED;
    }

    // Write the empty page to the file
    if (fwrite(emptyPage, sizeof(char), PAGE_SIZE, file) != PAGE_SIZE) {
        free(emptyPage);
        fclose(file);
        return RC_WRITE_FAILED;
    }

    free(emptyPage);
    fclose(file);
    return RC_OK;
}

// Open an existing page file
RC openPageFile(char *fileName, SM_FileHandle *fHandle) {
    FILE *file = fopen(fileName, "rb+");
    if (file == NULL) {
        return RC_FILE_NOT_FOUND;
    }

    fseek(file, 0, SEEK_END);
    long fileSize = ftell(file);
    rewind(file);

    fHandle->fileName = fileName;
    fHandle->totalNumPages = (int)(fileSize / PAGE_SIZE);
    fHandle->curPagePos = 0;
    fHandle->mgmtInfo = file;

    return RC_OK;
}

// Close an open page file
RC closePageFile(SM_FileHandle *fHandle) {
    if (fHandle == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }

    FILE *file = (FILE *)fHandle->mgmtInfo;
    if (file != NULL) {
        fclose(file);
        fHandle->mgmtInfo = NULL;
    }

    return RC_OK;
}

// Destroy (delete) a page file
RC destroyPageFile(char *fileName) {
    if (remove(fileName) != 0) {
        return RC_FILE_NOT_FOUND;
    }
    return RC_OK;
}

// Read a specific block/page from disk
RC readBlock(int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL || fHandle->mgmtInfo == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }

    if (pageNum < 0 || pageNum >= fHandle->totalNumPages) {
        return RC_READ_NON_EXISTING_PAGE;
    }

    FILE *file = (FILE *)fHandle->mgmtInfo;
    if (fseek(file, pageNum * PAGE_SIZE, SEEK_SET) != 0) {
        return RC_READ_NON_EXISTING_PAGE;
    }

    if (fread(memPage, sizeof(char), PAGE_SIZE, file) != PAGE_SIZE) {
        return RC_READ_NON_EXISTING_PAGE;
    }

    fHandle->curPagePos = pageNum;
    return RC_OK;
}

// Get the current block position
int getBlockPos(SM_FileHandle *fHandle) {
    if (fHandle == NULL) {
        return -1;
    }
    return fHandle->curPagePos;
}

// Read the first block
RC readFirstBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
    return readBlock(0, fHandle, memPage);
}

// Read the previous block
RC readPreviousBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL || fHandle->curPagePos <= 0) {
        return RC_READ_NON_EXISTING_PAGE;
    }
    return readBlock(fHandle->curPagePos - 1, fHandle, memPage);
}

// Read the current block
RC readCurrentBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }
    return readBlock(fHandle->curPagePos, fHandle, memPage);
}

// Read the next block
RC readNextBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL || fHandle->curPagePos >= fHandle->totalNumPages - 1) {
        return RC_READ_NON_EXISTING_PAGE;
    }
    return readBlock(fHandle->curPagePos + 1, fHandle, memPage);
}

// Read the last block
RC readLastBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }
    return readBlock(fHandle->totalNumPages - 1, fHandle, memPage);
}

// Write a page to disk using absolute position
RC writeBlock(int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL || fHandle->mgmtInfo == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }

    if (pageNum < 0 || pageNum > fHandle->totalNumPages) {
        return RC_WRITE_FAILED;
    }

    FILE *file = (FILE *)fHandle->mgmtInfo;
    if (fseek(file, pageNum * PAGE_SIZE, SEEK_SET) != 0) {
        return RC_WRITE_FAILED;
    }

    if (fwrite(memPage, sizeof(char), PAGE_SIZE, file) != PAGE_SIZE) {
        return RC_WRITE_FAILED;
    }

    fflush(file);
    fHandle->curPagePos = pageNum;

    if (pageNum == fHandle->totalNumPages) {
        fHandle->totalNumPages++;
    }

    return RC_OK;
}

// Write a page to disk using current position
RC writeCurrentBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
    if (fHandle == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }
    return writeBlock(fHandle->curPagePos, fHandle, memPage);
}

// Append an empty block to the file
RC appendEmptyBlock(SM_FileHandle *fHandle) {
    if (fHandle == NULL || fHandle->mgmtInfo == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }

    char *emptyBlock = (char *)calloc(PAGE_SIZE, sizeof(char));
    if (emptyBlock == NULL) {
        return RC_WRITE_FAILED;
    }

    RC result = writeBlock(fHandle->totalNumPages, fHandle, emptyBlock);
    free(emptyBlock);
    return result;
}

// Ensure the file has a certain number of pages
RC ensureCapacity(int numberOfPages, SM_FileHandle *fHandle) {
    if (fHandle == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    }

    while (fHandle->totalNumPages < numberOfPages) {
        RC result = appendEmptyBlock(fHandle);
        if (result != RC_OK) {
            return result;
        }
    }

    return RC_OK;
}