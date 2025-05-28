package Part3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

class Node {
    int id;
    String name;
    Node next;
    Node prev;

    Node(int id, String name) {
        this.id = id;
        this.name = name;
        next = null;
        prev = null;
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;

    DoublyLinkedList() {
        head = null;
        tail = null;
    }

    void add(int id, String name) {
        Node newNode = new Node(id, name);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    boolean remove(int id) {
        Node current = head;
        while (current != null) {
            if (current.id == id) {
                if (current == head) {
                    head = current.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = current.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    boolean contains(int id) {
        Node current = head;
        while (current != null) {
            if (current.id == id) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    void printList() {
        Node current = head;
        while (current != null) {
            System.out.println(current.id + " " + current.name);
            current = current.next;
        }
    }
}

class Main {
    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();
        Scanner scanner = new Scanner(System.in);

        // Read data from emp.txt and add to the Doubly Linked List
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\chait\\Desktop\\emp.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    dll.add(id, name);
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the initial Doubly Linked List
        System.out.println("Current Doubly Linked List:");
        dll.printList();
        System.out.println();

        boolean exitProgram = false;
        while (!exitProgram) {
            // Choose an option
            System.out.println("Choose an option:");
            System.out.println("1. Check if an element is present?");
            System.out.println("2. Add an element.");
            System.out.println("3. Remove an element.");
            System.out.println("4. Print the updated list.");
            System.out.println("5. Exit.");
            System.out.print("Enter your choice: ");
            int choice = 0;
            boolean validChoice = false;

            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    validChoice = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter an integer (1, 2, 3, 4, or 5).");
                    scanner.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter the ID to check: ");
                    int idToCheck = scanner.nextInt();
                    scanner.nextLine();
                    if (dll.contains(idToCheck)) {
                        System.out.println("ID " + idToCheck + " is present in the Doubly Linked List.");
                    } else {
                        System.out.println("ID " + idToCheck + " is not present in the Doubly Linked List.");
                    }
                    break;
                case 2:
                    System.out.print("Enter the ID to add: ");
                    int idToAdd = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the name to add: ");
                    String nameToAdd = scanner.nextLine();
                    dll.add(idToAdd, nameToAdd);
                    System.out.println("ID " + idToAdd + ", Name " + nameToAdd + " has been added to the Doubly Linked List.");
                    break;
                case 3:
                    System.out.print("Enter the ID to remove: ");
                    int idToRemove = scanner.nextInt();
                    scanner.nextLine(); 
                    if (dll.remove(idToRemove)) {
                        System.out.println("ID " + idToRemove + " has been removed from the Doubly Linked List.");
                    } else {
                        System.out.println("ID " + idToRemove + " is not present in the Doubly Linked List.");
                    }
                    break;
                case 4:
                    System.out.println("Current Doubly Linked List:");
                    dll.printList();
                    break;
                case 5:
                    exitProgram = true;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            System.out.println();
        }

        scanner.close();
    }
}
