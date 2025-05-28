package Project;

import java.util.Arrays;

public class BinarySearch<T extends Comparable<T>> {

    T[] nums; // Array to store the elements
    static long count; 

    public BinarySearch(T[] nums) {
        this.nums = nums;
        Arrays.sort(this.nums); // Ensuring the array is sorted before searching
    }
    
    // Method to perform binary search
    public int search(T elm) {
        count = 0;
        if (this.nums == null || this.nums.length == 0) {
            return -1; 
        }
        
        int low = 0;
        int high = nums.length - 1;
        
        // Loop to perform binary search
        while (low <= high) {
            count++;
            int mid = low + (high - low) / 2; 
            int cmp = elm.compareTo(nums[mid]);

            if (cmp == 0) {
                return mid; 
            } else if (cmp < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1; 
    }
}
