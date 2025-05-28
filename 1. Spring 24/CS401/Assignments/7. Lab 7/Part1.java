package Lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {

    static class Employee {
        int id;
        String name;

        public Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class Sorting {

        void SelectionSort(Employee[] array, int low, int high) {
            for (int i = low; i < high; i++) {
                int minIndex = i;
                for (int j = i + 1; j < high; j++) {
                    if (array[j].id < array[minIndex].id) {
                        minIndex = j;
                    }
                }
                Employee temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }

        int binarySearch(Employee[] array, int low, int high, int targetId) {
            if (low > high) {
                return -1; // Not found
            }
            int mid = low + (high - low) / 2;

            if (array[mid].id == targetId) {
                return mid; // Found
            } else if (array[mid].id < targetId) {
                return binarySearch(array, mid + 1, high, targetId); // Search right half
            } else {
                return binarySearch(array, low, mid - 1, targetId); // Search left half
            }
        }
    }

    public static void main(String[] args) {
        Sorting sort = new Sorting();
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            File file = new File("C:\\Users\\chait\\Desktop\\emp.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(" ");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                employees.add(new Employee(id, name));
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
            return;
        }

        Employee[] employeeArray = new Employee[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            employeeArray[i] = employees.get(i);
        }

        sort.SelectionSort(employeeArray, 0, employeeArray.length);

        System.out.println("Sorted Employee IDs and Names:");
        for (Employee emp : employeeArray) {
            System.out.println(emp.id + " " + emp.name);
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID to search: ");
        int targetId = input.nextInt();

        int index = sort.binarySearch(employeeArray, 0, employeeArray.length - 1, targetId);

        if (index != -1) {
            System.out.println("Employee found at index " + index + ": " + employeeArray[index].id + " " + employeeArray[index].name);
        } else {
            System.out.println("Employee not found!");
        }
    }
}