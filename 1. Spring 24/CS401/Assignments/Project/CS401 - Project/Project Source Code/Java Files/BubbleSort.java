package Project;

public class BubbleSort<T extends Comparable<T>> {
    private T[] nums; // Array to store elements
    private long count; 

    public BubbleSort(T[] nums) {
        this.nums = nums; // Initialize the array with input elements
    }

    public T[] sort() {
        if (nums == null || nums.length == 0) {
            return nums;
        }

        // Loop to iterate through the array for sorting
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                T cur = nums[j];
                T next = nums[j + 1];
                
                // Compare adjacent elements and swap if necessary
                if (cur.compareTo(next) > 0) {
                    count++;
                    T tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                }
            }
        }
        return nums; // Return the sorted array
    }

    public T[] getSortedArray() {
        return sort(); 
    }

    public long getCount() {
        return count; 
    }
}