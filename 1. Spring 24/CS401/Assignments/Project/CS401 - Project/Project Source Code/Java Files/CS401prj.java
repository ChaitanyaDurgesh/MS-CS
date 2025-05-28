package Project;

import java.util.Arrays;
import javax.swing.JOptionPane;

public class CS401prj {
  private static DataStructure<Integer> dataStructure;
  private static SortingAndSearching<Integer> sortingAndSearching;
  private static boolean listCreated = false;

  public static void main(String[] args) {
    dataStructure = new DataStructure<>(100);
    sortingAndSearching = new SortingAndSearching<>();
    while (true) {
      int choice = displayMenu();
      switch (choice) {
        case 0:
          createList();
          break;
        case 1:
          sortList();
          break;
        case 2:
          searchList();
          break;
        case 3:
          displayStatus();
          break;
        case 4:
          addData();
          break;
        case 5:
          deleteData();
          break;
        case 6:
          updateData();
          break;
        case 7:
          restoreData();
          break;
        case 8:
          analyzeData();
          break;
        case 9:
          JOptionPane.showMessageDialog(null, "Exiting program...");
          System.exit(0);
        default:
          JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
      }
    }
  }

  private static int displayMenu() {
    String[] options = { "Create List", "Sort List", "Search List", "Display Status", "Add Data", "Delete Data",
        "Update Data", "Restore Data", "Analyze Data", "Exit" };
    return JOptionPane.showOptionDialog(null, "Select an option:", "Menu", JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
  }

  private static void createList() {
	    String fileName = "C:\\Users\\chait\\Desktop\\emp.txt"; // Specify the file name
	    try {
	        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fileName));
	        String line;
	        int n = 0;
	        while ((line = reader.readLine()) != null) {
	            n++; // Count the number of lines
	        }
	        reader.close(); // Close the reader

	        Integer[] elements = new Integer[n];
	        reader = new java.io.BufferedReader(new java.io.FileReader(fileName)); // Reopen the reader
	        int i = 0;
	        while ((line = reader.readLine()) != null) {
	            elements[i++] = parseInput(line); // Parse each line as an integer
	        }
	        reader.close(); // Close the reader

	        for (Integer element : elements) {
	            dataStructure.add(element);
	        }
	        JOptionPane.showMessageDialog(null, "List created: " + Arrays.toString(dataStructure.toArray(new Integer[0])));
	        listCreated = true;
	    } catch (java.io.IOException e) {
	        JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
	    }
	}

  private static Integer parseInput(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Input should be integer");
    }
  }

  private static void sortList() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    Integer[] arr = dataStructure.toArray(new Integer[0]);
    sortingAndSearching.selectionSort(arr);
    sortingAndSearching.mergeSort(arr);
    JOptionPane.showMessageDialog(null, "Sorted list: " + Arrays.toString(arr) + "\nSelection Sort Comparisons: "
        + sortingAndSearching.getSelectionSortComparisons() + "\nMerge Sort Comparisons: "
        + sortingAndSearching.getMergeSortComparisons());
  }

  private static void searchList() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    Integer[] arr = dataStructure.toArray(new Integer[0]);
    String key = JOptionPane.showInputDialog("Enter the element to search:");
    Integer parsedKey = parseInput(key);
    boolean linearResult = sortingAndSearching.linearSearch(arr, parsedKey);
    Arrays.sort(arr);
    boolean binaryResult = sortingAndSearching.binarySearch(arr, parsedKey);
    HashTable<Integer> hashTable = new HashTable<>(arr.length);
    for (Integer element : arr) {
      hashTable.put(element);
    }
    boolean hashResult = sortingAndSearching.hashSearch(hashTable, parsedKey);
    JOptionPane.showMessageDialog(null, "Search Results:\nLinear search result: " + linearResult + "\nBinary search result: "
        + binaryResult + "\nHash search result: " + hashResult);
  }

  private static void displayStatus() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    Integer[] arr = dataStructure.toArray(new Integer[0]);
    JOptionPane.showMessageDialog(null, "Number of elements: " + dataStructure.size() + "\nList of elements: " + Arrays.toString(arr));
  }

  private static void addData() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    String element = JOptionPane.showInputDialog("Enter the element to add:");
    dataStructure.add(parseInput(element));
    JOptionPane.showMessageDialog(null, "Element added successfully.");
  }

  private static void deleteData() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    String element = JOptionPane.showInputDialog("Enter the element to delete:");
    boolean removed = dataStructure.remove(parseInput(element));
    if (removed) {
      JOptionPane.showMessageDialog(null, "Element deleted successfully.");
    } else {
      JOptionPane.showMessageDialog(null, "Element not found in the list.");
    }
  }

  private static void updateData() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    String oldElement = JOptionPane.showInputDialog("Enter the element to update:");
    String newElement = JOptionPane.showInputDialog("Enter the new element:");
    boolean updated = dataStructure.update(parseInput(oldElement), parseInput(newElement));
    if (updated) {
      JOptionPane.showMessageDialog(null, "Element updated successfully.");
    } else {
      JOptionPane.showMessageDialog(null, "Element not found in the list.");
    }
  }

  private static void restoreData() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }
    String element = JOptionPane.showInputDialog("Enter the element to restore:");
    dataStructure.restore(parseInput(element));
    JOptionPane.showMessageDialog(null, "Element restored successfully.");
  }

  private static void analyzeData() {
    if (!listCreated) {
      JOptionPane.showMessageDialog(null, "Please create a list first.");
      return;
    }

    long startTime = System.nanoTime();
    // Perform the analysis operations here
    long endTime = System.nanoTime();

    double executionTime = (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

    JOptionPane.showMessageDialog(null, "Data Analysis Report:\n" +
        "Time Complexities of Operations:\n" +
        "Add Data: O(1)\n" +
        "Delete Data: O(n)\n" +
        "Update Data: O(n)\n" +
        "Restore Data: O(1)\n" +
        "Linear Search: O(n)\n" +
        "Binary Search: O(log n)\n" +
        "Hash Search: O(1)\n" +
        "Selection Sort: O(n^2)\n" +
        "Merge Sort: O(n log n)\n" +
        "Execution Time for Analysis: " + executionTime + "s");
  }

  private static class DataStructure<T extends Comparable<T>> {
    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public DataStructure(int capacity) {
      data = (T[]) new Comparable[capacity];
      size = 0;
    }

    public void add(T element) {
      ensureCapacity();
      data[size++] = element;
    }

    private void ensureCapacity() {
      if (size == data.length) {
        data = Arrays.copyOf(data, data.length * 2);
      }
    }

    public T get(int index) {
      if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      }
      return data[index];
    }

    public int size() {
      return size;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(T[] arr) {
      return (T[]) Arrays.copyOf(data, size, arr.getClass());
    }

    public boolean remove(T element) {
      int index = indexOf(element);
      if (index == -1) {
        return false;
      }
      removeAt(index);
      return true;
    }

    private int indexOf(T element) {
      for (int i = 0; i < size; i++) {
        if (data[i].equals(element)) {
          return i;
        }
      }
      return -1;
    }

    private void removeAt(int index) {
      System.arraycopy(data, index + 1, data, index, size - index - 1);
      size--;
    }

    public boolean update(T oldElement, T newElement) {
      int index = indexOf(oldElement);
      if (index == -1) {
        return false;
      }
      data[index] = newElement;
      return true;
    }

    public void restore(T element) {
      add(element);
    }
  }

  private static class SortingAndSearching<T extends Comparable<T>> {
    private int selectionSortComparisons;
    private int mergeSortComparisons;

    public void selectionSort(T[] arr) {
      selectionSortComparisons = 0;
      for (int i = 0; i < arr.length - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < arr.length; j++) {
          selectionSortComparisons++;
          if (arr[j].compareTo(arr[minIndex]) < 0) {
            minIndex = j;
          }
        }
        swap(arr, i, minIndex);
      }
    }

    public void mergeSort(T[] arr) {
      mergeSortComparisons = 0;
      auxMergeSort(arr, 0, arr.length - 1);
    }

    private void auxMergeSort(T[] arr, int low, int high) {
      if (low < high) {
        int mid = low + (high - low) / 2;
        auxMergeSort(arr, low, mid);
        auxMergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high);
      }
    }

    private void merge(T[] arr, int low, int mid, int high) {
      T[] aux = Arrays.copyOf(arr, arr.length);
      int i = low, j = mid + 1;
      for (int k = low; k <= high; k++) {
        mergeSortComparisons++;
        if (i > mid) {
          arr[k] = aux[j++];
        } else if (j > high) {
          arr[k] = aux[i++];
        } else if (aux[j].compareTo(aux[i]) < 0) {
          arr[k] = aux[j++];
        } else {
          arr[k] = aux[i++];
        }
      }
    }

    private void swap(T[] arr, int i, int j) {
      T temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }

    public boolean linearSearch(T[] arr, T key) {
      for (T element : arr) {
        if (element.compareTo(key) == 0) {
          return true;
        }
      }
      return false;
    }

    public boolean binarySearch(T[] arr, T key) {
      int low = 0, high = arr.length - 1;
      while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid].compareTo(key) == 0) {
          return true;
        } else if (arr[mid].compareTo(key) < 0) {
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }
      return false;
    }

    public boolean hashSearch(HashTable<T> hashTable, T key) {
      int index = hashFunction(key, hashTable.size());
      if (hashTable.get(index) != null && hashTable.get(index).compareTo(key) == 0) { // Fixed hashSearch implementation [3]
        return true;
      }
      return false;
    }

    private int hashFunction(T key, int tableSize) {
      return Math.abs(key.hashCode()) % tableSize; // Using hashCode() method [3]
    }

    public int getSelectionSortComparisons() {
      return selectionSortComparisons;
    }

    public int getMergeSortComparisons() {
      return mergeSortComparisons;
    }
  }

  private static class HashTable<T extends Comparable<T>> { // Using generics correctly [6]
    private T[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
      table = (T[]) new Comparable[capacity];
      size = 0;
    }

    public void put(T element) {
      int index = hashFunction(element, table.length);
      if (table[index] == null) {
        table[index] = element;
        size++;
      }
    }

    public T get(int index) {
      return table[index];
    }

    public int size() {
      return size;
    }

    private int hashFunction(T element, int tableSize) {
      return Math.abs(element.hashCode()) % tableSize; // Using hashCode() method [3]
    }
  }
}