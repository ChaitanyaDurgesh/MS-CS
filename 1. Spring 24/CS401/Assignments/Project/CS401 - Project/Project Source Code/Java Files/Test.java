package Project;

import java.util.Arrays;
import javax.swing.JOptionPane;

public class Test {

    public static void main(String[] args) {
        // Test data setup
        Integer[] numbers = {69, 75, 23, 38, 15, 13};
        Integer[] sortedNumbers = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(sortedNumbers); // Sorted array for binary search and to verify sort algorithms

        // Instantiate and test the sorting algorithms
        StringBuilder sortingResults = new StringBuilder();
        sortingResults.append("Bubble Sort Result: ").append(Arrays.toString(testBubbleSort(numbers))).append("\n");
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n");
        sortingResults.append("Quick Sort Result: ").append(Arrays.toString(testQuickSort(numbers))).append("\n\n");
        StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append("\n\n");

        // Instantiate and test the search algorithms
        sortingResults.append("Linear Search Result: Element found at index ").append(testLinearSearch(numbers, 23)).append("\n");
        sortingResults.append("Linear Search Comparisons: ").append(LinearSearch.count).append("\n\n");
        sortingResults.append("Binary Search Result: Element found at index ").append(testBinarySearch(sortedNumbers, 23)).append("\n");
        sortingResults.append("Binary Search Comparisons: ").append(BinarySearch.count).append("\n\n");
        sortingResults.append("Hash Search Result: Element found in bucket index ").append(testHashSearch(numbers, 23)).append("\n");
        sortingResults.append("Hash Search Comparisons: ").append(HashSearch.count).append("\n\n");

        // Instantiate and test the data manipulation
        testDataManipulation();

        // Display the results using JOptionPane
        JOptionPane.showMessageDialog(null, sortingResults.toString(), "Test Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private static Integer[] testBubbleSort(Integer[] data) {
        BubbleSort<Integer> bubbleSort = new BubbleSort<>(Arrays.copyOf(data, data.length));
        return bubbleSort.sort();
    }

    private static Integer[] testQuickSort(Integer[] data) {
        QuickSort<Integer> quickSort = new QuickSort<>(Arrays.copyOf(data, data.length));
        quickSort.sort(0, data.length - 1);
        return quickSort.getSortedArray();
    }

    private static int testLinearSearch(Integer[] data, Integer target) {
        LinearSearch<Integer> linearSearch = new LinearSearch<>(data);
        return linearSearch.search(target);
    }

    private static int testBinarySearch(Integer[] data, Integer target) {
        BinarySearch<Integer> binarySearch = new BinarySearch<>(data);
        return binarySearch.search(target);
    }

    private static int testHashSearch(Integer[] data, Integer target) {
        HashSearch<Integer> hashSearch = new HashSearch<>(data);
        return hashSearch.search(target);
    }

    private static void testDataManipulation() {
        DataManipulator dataManipulator = new DataManipulator(Arrays.asList(1, 2, 3, 4, 5));
        dataManipulator.addData(6);
        dataManipulator.deleteData(3);
        dataManipulator.updateData(0, 10);
        dataManipulator.restoreData(3);
        dataManipulator.analyzeData();
    }
}