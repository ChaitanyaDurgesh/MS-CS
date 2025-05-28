#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "buffer_mgr.h"
#include "storage_mgr.h"

// Represents a single page frame in the buffer pool (in memory).
typedef struct Page {
    SM_PageHandle data;  // Holds the actual page data.
    PageNumber pageNum;  // Unique identifier for each page.
    int dirtyBit;        // Indicates if the page was modified.
    int fixCount;        // Number of clients using the page.
    int referenceCount;  // Used for replacement strategies like LRU.
} Page;

// Structure to manage the buffer pool.
typedef struct BufferPoolInfo {
    Page *pages;          // Array of pages in the buffer pool.
    int numPages;         // Total number of pages.
    int nextVictimIndex;  // Used for round-robin or FIFO replacement.
    ReplacementStrategy strategy; // The replacement strategy used.
    int numReads;         // Track the number of pages read from disk.
    int numWrites;        // Track the number of pages written to disk.
} BufferPoolInfo;

// Internal function prototypes.
static void pinPageHelper(BufferPoolInfo *bufferInfo, Page *page);
static Page *findPage(BufferPoolInfo *bufferInfo, PageNumber pageNum);

// Initializes the buffer pool.
RC initBufferPool(BM_BufferPool *const bm, const char *const pageFileName,
                  const int numPages, ReplacementStrategy strategy,
                  void *stratData) {
    bm->pageFile = (char *)pageFileName;
    bm->numPages = numPages;
    bm->strategy = strategy;

    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)malloc(sizeof(BufferPoolInfo));
    bufferInfo->pages = (Page *)calloc(numPages, sizeof(Page));
    bufferInfo->numPages = numPages;
    bufferInfo->nextVictimIndex = 0;
    bufferInfo->strategy = strategy;
    bufferInfo->numReads = 0;
    bufferInfo->numWrites = 0;

    bm->mgmtData = bufferInfo;
    return RC_OK;
}

// Shuts down the buffer pool, ensuring all dirty pages are written back.
RC shutdownBufferPool(BM_BufferPool *const bm) {
    forceFlushPool(bm);
    free(((BufferPoolInfo *)bm->mgmtData)->pages);
    free(bm->mgmtData);
    return RC_OK;
}

// Forces all dirty pages with fixCount == 0 to be written to disk.
RC forceFlushPool(BM_BufferPool *const bm) {
    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)bm->mgmtData;
    SM_FileHandle smFileHandle;

    // Open the page file for writing.
    if (openPageFile(bm->pageFile, &smFileHandle) != RC_OK) {
        return RC_FILE_NOT_FOUND;
    }

    for (int i = 0; i < bufferInfo->numPages; i++) {
        Page *page = &bufferInfo->pages[i];
        if (page->dirtyBit && page->fixCount == 0) {
            writeBlock(page->pageNum, &smFileHandle, page->data);
            bufferInfo->numWrites++;
            page->dirtyBit = 0;
        }
    }

    closePageFile(&smFileHandle);
    return RC_OK;
}

// Marks a page as dirty.
RC markDirty(BM_BufferPool *const bm, BM_PageHandle *const page) {
    Page *targetPage = findPage((BufferPoolInfo *)bm->mgmtData, page->pageNum);
    if (!targetPage) return RC_PAGE_NOT_FOUND;
    targetPage->dirtyBit = 1;
    return RC_OK;
}

// Unpins a page from the buffer pool.
RC unpinPage(BM_BufferPool *const bm, BM_PageHandle *const page) {
    Page *targetPage = findPage((BufferPoolInfo *)bm->mgmtData, page->pageNum);
    if (!targetPage) return RC_PAGE_NOT_FOUND;
    targetPage->fixCount--;
    return RC_OK;
}

// Writes the contents of a page to disk.
RC forcePage(BM_BufferPool *const bm, BM_PageHandle *const page) {
    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)bm->mgmtData;
    SM_FileHandle smFileHandle;

    // Open the page file for writing.
    if (openPageFile(bm->pageFile, &smFileHandle) != RC_OK) {
        return RC_FILE_NOT_FOUND;
    }

    Page *targetPage = findPage(bufferInfo, page->pageNum);
    if (!targetPage) return RC_PAGE_NOT_FOUND;

    writeBlock(page->pageNum, &smFileHandle, targetPage->data);
    bufferInfo->numWrites++;
    targetPage->dirtyBit = 0;

    closePageFile(&smFileHandle);
    return RC_OK;
}

// Pins a page into the buffer pool, loading it from disk if necessary.
RC pinPage(BM_BufferPool *const bm, BM_PageHandle *const page,
           const PageNumber pageNum) {
    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)bm->mgmtData;
    Page *targetPage = findPage(bufferInfo, pageNum);

    if (!targetPage) {
        SM_FileHandle smFileHandle;

        if (openPageFile(bm->pageFile, &smFileHandle) != RC_OK) {
            return RC_FILE_NOT_FOUND;
        }

        int victim = bufferInfo->nextVictimIndex;
        targetPage = &bufferInfo->pages[victim];

        if (targetPage->dirtyBit) {
            writeBlock(targetPage->pageNum, &smFileHandle, targetPage->data);
            bufferInfo->numWrites++;
        }

        readBlock(pageNum, &smFileHandle, targetPage->data);
        bufferInfo->numReads++;

        targetPage->pageNum = pageNum;
        targetPage->dirtyBit = 0;
        targetPage->fixCount = 1;
        bufferInfo->nextVictimIndex = (victim + 1) % bufferInfo->numPages;

        closePageFile(&smFileHandle);
    } else {
        pinPageHelper(bufferInfo, targetPage);
    }

    page->pageNum = pageNum;
    page->data = targetPage->data;
    return RC_OK;
}


// Returns an array with the page numbers in the buffer pool.
PageNumber *getFrameContents(BM_BufferPool *const bm) {
    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)bm->mgmtData;
    PageNumber *frameContents = (PageNumber *)malloc(sizeof(PageNumber) * bm->numPages);

    for (int i = 0; i < bm->numPages; i++) {
        frameContents[i] = bufferInfo->pages[i].pageNum;
    }
    return frameContents;
}

// Returns an array with the dirty flags of the pages in the buffer pool.
bool *getDirtyFlags(BM_BufferPool *const bm) {
    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)bm->mgmtData;
    bool *dirtyFlags = (bool *)malloc(sizeof(bool) * bm->numPages);

    for (int i = 0; i < bm->numPages; i++) {
        dirtyFlags[i] = (bufferInfo->pages[i].dirtyBit != 0);
    }
    return dirtyFlags;
}

// Returns an array with the fix counts of the pages in the buffer pool.
int *getFixCounts(BM_BufferPool *const bm) {
    BufferPoolInfo *bufferInfo = (BufferPoolInfo *)bm->mgmtData;
    int *fixCounts = (int *)malloc(sizeof(int) * bm->numPages);

    for (int i = 0; i < bm->numPages; i++) {
        fixCounts[i] = bufferInfo->pages[i].fixCount;
    }
    return fixCounts;
}


// Helper function to pin a page by incrementing its fix count.
static void pinPageHelper(BufferPoolInfo *bufferInfo, Page *page) {
    page->fixCount++;
}

// Finds a page in the buffer pool by its page number.
static Page *findPage(BufferPoolInfo *bufferInfo, PageNumber pageNum) {
    for (int i = 0; i < bufferInfo->numPages; i++) {
        if (bufferInfo->pages[i].pageNum == pageNum) {
            return &bufferInfo->pages[i];
        }
    }
    return NULL;
}

// Retrieves the number of page reads.
int getNumReadIO(BM_BufferPool *const bm) {
    return ((BufferPoolInfo *)bm->mgmtData)->numReads;
}

// Retrieves the number of page writes.
int getNumWriteIO(BM_BufferPool *const bm) {
    return ((BufferPoolInfo *)bm->mgmtData)->numWrites;
}
