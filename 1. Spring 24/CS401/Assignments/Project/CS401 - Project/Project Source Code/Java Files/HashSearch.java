package Project;

import java.util.LinkedList;
import java.util.List;

public class HashSearch<T extends Comparable<T>> {

    T[] nums; // Array to store elements
    static long count; 
    int mod = 64;  
    List<LinkedList<T>> list; // List of linked lists to store hash buckets

    public HashSearch(T[] nums) {
        this.nums = nums;
        this.list = new LinkedList<>(); // Initialize the list with a LinkedList of LinkedLists

        // Initialize the list with empty LinkedLists 
        for (int i = 0; i < mod; i++) {
            list.add(new LinkedList<>());
        }

        // Populate the hash buckets with elements from the input array
        for (T num : nums) {
            int hash = getHash(num);
            insertSorted(list.get(hash), num);
        }
    }

    private void insertSorted(LinkedList<T> bucket, T num) {
        if (bucket.isEmpty()) {
            bucket.add(num);
        } else {
            int pos = 0;
            for (T item : bucket) {
                if (item.compareTo(num) >= 0) {
                    break;
                }
                pos++;
            }
            bucket.add(pos, num); // Insert in sorted order
        }
    }

    // Method to search for an element in the hash buckets
    public int search(T elm) {
        count = 0;
        int hash = getHash(elm);
        LinkedList<T> bucket = list.get(hash);

        // Binary search in the bucket
        int low = 0, high = bucket.size() - 1;
        while (low <= high) {
            count++;
            int mid = low + (high - low) / 2;
            T item = bucket.get(mid);
            int cmp = item.compareTo(elm);
            if (cmp == 0) {
                return mid; 
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1; 
    }

    // Method to calculate the hash value for an element
    public int getHash(T elm) {
        return Math.abs(elm.hashCode()) % mod; 
    }
}