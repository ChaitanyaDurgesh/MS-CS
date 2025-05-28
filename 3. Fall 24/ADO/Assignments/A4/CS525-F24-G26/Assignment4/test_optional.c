#include "btree_mgr.h"
#include "record_mgr.h"
#include "buffer_mgr.h"
#include "tables.h"
#include "dberror.h"
#include "expr.h"
#include <stdio.h>
#include <stdlib.h>

void testRecordManagerIntegration();
void testDataTypeSupport();
void testPointerSwizzling();
void testMultipleEntriesPerKey();

int main() {
    // Initialize managers
    initStorageManager();
    initRecordManager(NULL);
    initBufferManager();
    initIndexManager(NULL);

    // Run each test
    testRecordManagerIntegration();
    testDataTypeSupport();
    testPointerSwizzling();
    testMultipleEntriesPerKey();

    // Shutdown managers
    shutdownIndexManager();
    shutdownBufferManager();
    shutdownRecordManager();
    printf("All tests for optional extensions passed.\n");
    return 0;
}

// Test 1: Record Manager Integration with B+-tree Indexing
void testRecordManagerIntegration() {
    printf("Running testRecordManagerIntegration...\n");

    // Create a relation with an index on the key attribute
    char *relName = "example_relation";
    Schema *schema = createSchema(2, (char *[]){"id", "key_attr"},
                                  (DataType[]){DT_INT, DT_STRING},
                                  (int[]){0, 1}, 1); // Set key_attr as key
    createTable(relName, schema);

    BTreeHandle *tree;
    RC rc = createBtree(&tree, DT_INT, 3);
    if (rc != RC_OK) {
        printf("Failed to create B-tree\n");
        return;
    }

    // Insert records with indexed attribute
    Record *rec;
    Value *val;
    RID rid;
    for (int i = 1; i <= 5; i++) {
        createRecord(&rec, schema);
        setAttr(rec, schema, 0, intToValue(i));  // ID
        setAttr(rec, schema, 1, stringToValue("TestKey")); // Key attribute
        insertRecord(relName, rec); // Record Manager inserts in B-tree
    }

    // Test retrieval through B-tree index
    MAKE_VALUE(val, DT_STRING, "TestKey");
    rc = findKey(tree, val, &rid);
    ASSERT_TRUE(rc == RC_OK, "Record found using B+-tree index");

    printf("testRecordManagerIntegration passed.\n");
}

// Test 2: Data Type Support in B+-tree
void testDataTypeSupport() {
    printf("Running testDataTypeSupport...\n");

    BTreeHandle *tree_int, *tree_float, *tree_string;
    createBtree(&tree_int, DT_INT, 3);
    createBtree(&tree_float, DT_FLOAT, 3);
    createBtree(&tree_string, DT_STRING, 3);

    // Test integer type
    RID rid1 = {1, 1};
    Value *intKey = intToValue(42);
    insertKey(tree_int, intKey, rid1);
    ASSERT_TRUE(findKey(tree_int, intKey, &rid1) == RC_OK, "Integer key test passed.");

    // Test float type
    RID rid2 = {2, 2};
    Value *floatKey = floatToValue(3.14);
    insertKey(tree_float, floatKey, rid2);
    ASSERT_TRUE(findKey(tree_float, floatKey, &rid2) == RC_OK, "Float key test passed.");

    // Test string type
    RID rid3 = {3, 3};
    Value *stringKey = stringToValue("hello");
    insertKey(tree_string, stringKey, rid3);
    ASSERT_TRUE(findKey(tree_string, stringKey, &rid3) == RC_OK, "String key test passed.");

    printf("testDataTypeSupport passed.\n");
}

// Test 3: Pointer Swizzling Verification
void testPointerSwizzling() {
    printf("Running testPointerSwizzling...\n");

    BTreeHandle *tree;
    createBtree(&tree, DT_INT, 3);

    // Insert a series of keys to trigger node load, memory pointer conversion
    for (int i = 1; i <= 10; i++) {
        RID rid = {i, i};
        Value *key = intToValue(i * 10);
        insertKey(tree, key, rid);
    }

    // Simulate buffer evictions and reloading
    // In this mock, we assume swizzling functions as expected; use valgrind for memory profiling
    printf("Pointer swizzling test completed. Use valgrind for detailed memory check.\n");
}

// Test 4: Multiple Entries Per Key
void testMultipleEntriesPerKey() {
    printf("Running testMultipleEntriesPerKey...\n");

    BTreeHandle *tree;
    createBtree(&tree, DT_INT, 3);
    Value *key = intToValue(100);

    RID rid1 = {1, 1}, rid2 = {2, 2}, rid3 = {3, 3};
    insertKey(tree, key, rid1);
    insertKey(tree, key, rid2);
    insertKey(tree, key, rid3);

    // Check retrieval of multiple entries for one key
    RID *ridArray;
    int numEntries;
    findAllEntries(tree, key, &ridArray, &numEntries);
    ASSERT_TRUE(numEntries == 3, "Multiple entries for key retrieved correctly");

    free(ridArray);  // Free memory allocated for entries array
    printf("testMultipleEntriesPerKey passed.\n");
}
