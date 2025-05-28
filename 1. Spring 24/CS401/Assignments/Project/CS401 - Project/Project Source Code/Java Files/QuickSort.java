package Project;

import java.util.Random;

public class QuickSort<T extends Comparable<T>> {

    private T[] nums; // Array to be sorted
    private long count = 0; // Variable to get the number of comparisons
    private Random random; 

    public QuickSort(T[] nums) {
        this.nums = nums; // Initializing array to be sorted
        random = new Random();  
    }
    
    // Method to perform quick sort on the array
    public void sort(int low, int high) {
        if (low >= high) {
            return; 
        }
        int total = high - low + 1;
        int ind = random.nextInt(total);
        swap(low, low + ind);
        int left = low + 1;
        int right = high;
        while (left <= right) {
            count++;
            if (nums[left].compareTo(nums[low]) <= 0) {
                left++;
            } else {
                swap(left, right);
                right--;
            }
        }
        swap(low, left - 1);
        
        // Recursively sorting
        sort(low, left - 2);
        sort(left, high);
    }
    
    // Method to swap two elements in the array
    private void swap(int i, int j) {
        if (i > j)
            return;
        T tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    public T[] getSortedArray() {
        return nums; // Return the sorted array
    }
    
    public long getCount() {
        return count;
    }
}