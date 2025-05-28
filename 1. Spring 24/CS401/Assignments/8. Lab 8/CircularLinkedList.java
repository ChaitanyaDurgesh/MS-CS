package Lab8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Employee {
    int id;
    String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class CircularLinkedList {
    class Node {
        Employee data;
        Node next;

        Node(Employee data) {
            this.data = data;
            next = null;
        }
    }

    private Node head;

    public void insert(Employee data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            Node temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = head;
        }
    }

    public void display() {
        if (head == null) {
            System.out.println("Circular Linked List is empty.");
            return;
        }

        Node temp = head;
        do {
            System.out.println(temp.data.id + " " + temp.data.name);
            temp = temp.next;
        } while (temp != head);
    }

    public void delete(int id) {
        if (head == null) {
            System.out.println("Circular Linked List is empty.");
            return;
        }

        if (head.data.id == id) {
            if (head.next == head) {
                head = null;
                return;
            }

            Node temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = head.next;
            head = head.next;
            return;
        }

        Node prev = head;
        Node curr = head.next;
        while (curr != head) {
            if (curr.data.id == id) {
                prev.next = curr.next;
                return;
            }
            prev = curr;
            curr = curr.next;
        }

        System.out.println("Employee with ID " + id + " not found.");
    }

    public static void main(String[] args) {
        CircularLinkedList cll = new CircularLinkedList();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\chait\\Desktop\\emp.txt"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        Employee emp = new Employee(id, name);
                        cll.insert(emp);
                        count++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format in line: " + line);
                    }
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
                if (count >= 8) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Elements after inserting:");
        cll.display();

        cll.delete(3);

        System.out.println("\nElements after deleting:");
        cll.display();
    }
}