package Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManipulator {
    private List<Integer> dataList;
    private List<Integer> deletedElements = new ArrayList<>();
    private String lastAction = "No actions performed yet.";

    // Constructor for initializing with existing data
    public DataManipulator(List<Integer> initialData) {
        this.dataList = new ArrayList<>(initialData);
    }

    // Default constructor
    public DataManipulator() {
        this.dataList = new ArrayList<>();
    }

    // Adds data to the list
    public void addData(Integer data) {
        dataList.add(data);
        lastAction = "Added data: " + data;
    }

    // Deletes data from the list by value
    public void deleteData(Integer data) {
        if (dataList.remove(data)) {
            deletedElements.add(data);
            lastAction = "Deleted data: " + data;
        } else {
            System.out.println("Data not found in the list.");
        }
    }

    // Updates data at a specific index
    public void updateData(int index, Integer newData) {
        if (index >= 0 && index < dataList.size()) {
            Integer oldData = dataList.get(index);
            dataList.set(index, newData);
            lastAction = "Updated data from " + oldData + " to " + newData;
        } else {
            System.out.println("Invalid index. No update performed.");
        }
    }

    // Restores previously deleted data
    public void restoreData(Integer data) {
        if (deletedElements.contains(data)) {
            dataList.add(data);
            deletedElements.remove(data);
            lastAction = "Restored data: " + data;
        } else {
            System.out.println("Data not found in the deleted elements list.");
        }
    }

    // Analyzes and prints a detailed report 
    public void analyzeData() {
        System.out.println("Analyzing Data:");
        System.out.println("Current data quantity: " + getDataSize());
        System.out.println("Last action: " + lastAction);
        System.out.println("Deleted elements: " + deletedElements);
        System.out.println("\nUpdated data list:");
        displayData();
    }

    // Displays the data list
    public void displayData() {
        for (Integer data : dataList) {
            System.out.println(data);
        }
    }

    // Returns the size of the data list
    public int getDataSize() {
        return dataList.size();
    }

    // Accepts user input for adding data
    public void acceptUserInputForAddingData() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter data to add (numeric values only):");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // Clear incorrect input
            }
            Integer data = scanner.nextInt();
            addData(data);
            System.out.println("Data added successfully.");
            analyzeData();
        }
    }

    // Accepts user input for deleting data by index
    public void acceptUserInputForDeletingData() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter index of data to delete:");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer index.");
                scanner.next(); // Clear incorrect input
            }
            int index = scanner.nextInt();
            if (index >= 0 && index < dataList.size()) {
                deleteData(dataList.get(index));
                System.out.println("Data deleted successfully.");
                analyzeData(); 
            } else {
                System.out.println("Invalid index.");
            }
        }
    }

    // Accepts user input for updating data
    public void acceptUserInputForUpdatingData() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter index of data to update:");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer index.");
                scanner.next();
            }
            int index = scanner.nextInt();
            System.out.println("Enter new data (numeric values only):");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); 
            }
            Integer newData = scanner.nextInt();
            updateData(index, newData);
            System.out.println("Data updated successfully.");
            analyzeData(); // Call analyzeData() after updating data
        }
    }

    // Accepts user input for restoring data
    public void acceptUserInputForRestoringData() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter data to restore (numeric values only):");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); 
            }
            Integer data = scanner.nextInt();
            restoreData(data);
            System.out.println("Data restored successfully.");
            analyzeData(); // Call analyzeData() after restoring data
        }
    }
}