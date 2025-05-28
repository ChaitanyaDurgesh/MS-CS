package Project;

public class LinearSearch<T extends Comparable<T>> {

    T[] nums; // Array to store elements
    static long count; // Variable to track the number of comparisons

    public LinearSearch(T[] nums) {
        this.nums = nums; // Initialize the array with input elements
    }
    
 // Method to perform linear search for the given element
    public int search(T elm) {
        count = 0;
        for (int i = 0; i < nums.length; i++) {
            count++;
            if (nums[i].compareTo(elm) == 0) {
                return i;
            }
        }
        return -1;
    }

}