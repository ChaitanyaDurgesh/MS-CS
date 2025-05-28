package Lab6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\chait\\Desktop\\emp.txt"))) {
            Queue fixedFrontQueue = new Queue(10); //Fixed front queue with capacity 10
            Queue floatingFrontQueue = new Queue(10); //Floating front queue with capacity 10

            String line;
            int count = 0;

            while ((line = br.readLine()) != null && count < 10) {
                String[] parts = line.split(" ");
                Employee emp = new Employee(Integer.parseInt(parts[0]), parts[1]);

                fixedFrontQueue.enqueue(emp);
                floatingFrontQueue.enqueue(emp);

                count++;
            }

            System.out.println("Elements stored in Fixed Front Queue:");
            fixedFrontQueue.printQueue();

            for (int i = 0; i < 3; i++) {
                fixedFrontQueue.dequeue(); // Dequeue 3 elements from fixed front queue
            }

            System.out.println("\nElements stored in Fixed Front Queue after dequeueing thrice:");
            fixedFrontQueue.printQueue();

            System.out.println("\n\n\nElements stored in Floating Front Queue:");
            floatingFrontQueue.printQueue();

            for (int i = 0; i < 3; i++) {
                floatingFrontQueue.dequeue(); // Dequeue 3 elements from floating front queue
            }

            System.out.println("\nElements stored in Floating Front Queue after dequeueing thrice:");
            floatingFrontQueue.printQueue();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}