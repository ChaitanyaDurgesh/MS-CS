import java.util.Random;

public class SelectionSort {

    public static void main(String[] args) {
        int[] values = generateRandomArray(10000);
        System.out.println("Original array:");
        printArray(values);

        long startTime = System.currentTimeMillis();
        selectionSort(values);
        long endTime = System.currentTimeMillis();

        System.out.println("\n Sorted array:");
        printArray(values);

        System.out.println("\n Execution time: " + (endTime - startTime) + " ms");
    }

    public static int minIndex(int[] values, int startIndex, int endIndex) {
        //returns the index of smallest value in
    	//values[startIndex]..values[endIndex]
    	int indexOfMin = startIndex;
        for (int index = startIndex + 1; index <= endIndex; index++) {
            if (values[index] < values[indexOfMin]) {
                indexOfMin = index;
            }
        }
        return indexOfMin;
    }

    // Sorts the given array using selection sort algorithm
    public static void selectionSort(int[] values) {
        for (int current = 0; current < values.length - 1; current++) {
            int minIndex = minIndex(values, current, values.length - 1);
            swap(values, current, minIndex);
        }
    }

    // Swaps two elements[lower number and higher number] in the given array
    public static void swap(int[] values, int index1, int index2) {
        int temp = values[index1];
        values[index1] = values[index2];
        values[index2] = temp;
    }

    // Generates an array of random integer values of the given size 10000
    public static int[] generateRandomArray(int size) {
        int[] values = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt();
        }
        return values;
    }

    public static void printArray(int[] values) {
        for (int value : values) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
