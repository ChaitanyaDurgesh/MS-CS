package Lab8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Employee {
    int id;
    String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Node {
    Employee data;
    Node next;

    Node(Employee data) {
        this.data = data;
        next = null;
    }
}

class LinkedList {
    protected Node head;

    public void add(Employee data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void remove(int id) {
        if (head == null) {
            return;
        }

        if (head.data.id == id) {
            head = head.next;
            return;
        }

        Node temp = head;
        while (temp.next != null) {
            if (temp.next.data.id == id) {
                temp.next = temp.next.next;
                return;
            }
            temp = temp.next;
        }
    }

    public boolean contains(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.id == id) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
}

class SortedLinkedList extends LinkedList {
    @Override
    public void add(Employee data) {
        Node newNode = new Node(data);
        if (head == null || head.data.id >= data.id) {
            newNode.next = head;
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null && temp.next.data.id < data.id) {
                temp = temp.next;
            }
            newNode.next = temp.next;
            temp.next = newNode;
        }
    }

    public void remove(int id) {
        if (head == null) {
            return;
        }

        if (head.data.id == id) {
            head = head.next;
            return;
        }

        Node temp = head;
        while (temp.next != null) {
            if (temp.next.data.id == id) {
                temp.next = temp.next.next;
                return;
            }
            temp = temp.next;
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public boolean contains(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.id == id) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void printSortedList() {
        System.out.println("\nCurrent Sorted List:");
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data.id + " " + temp.data.name);
            temp = temp.next;
        }
    }
}

public class Part1 {
    public static void main(String[] args) {
        SortedLinkedList sortedList = new SortedLinkedList();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\chait\\Desktop\\emp.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        Employee emp = new Employee(id, name);
                        sortedList.add(emp);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format in line: " + line);
                    }
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the initial sorted list
        sortedList.printSortedList();

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add employee");
            System.out.println("2. Remove employee");
            System.out.println("3. Check if employee exists");
            System.out.println("4. Print the updated list");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter the ID to add: ");
                    int addId = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter the name to add: ");
                    String addName = scanner.nextLine();
                    Employee newEmp = new Employee(addId, addName);
                    sortedList.add(newEmp);
                    System.out.println("Added employee with ID " + addId + " and Name " + addName);
                    break;
                case 2:
                    System.out.print("Enter the ID to remove: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine(); 
                    sortedList.remove(removeId);
                    System.out.println("Removed employee with ID " + removeId);
                    break;
                case 3:
                    System.out.print("Enter the ID to search: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); 
                    if (sortedList.contains(searchId)) {
                        System.out.println("Employee with ID " + searchId + " exists in the list.");
                    } else {
                        System.out.println("Employee with ID " + searchId + " does not exist in the list.");
                    }
                    break;
                case 4:
                    sortedList.printSortedList();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}