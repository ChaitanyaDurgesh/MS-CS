package lab5;

import java.util.Scanner;

public class Jacobsthal  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the value of n: ");
        int n = scanner.nextInt();

        // Recursive method
        long startTime = System.nanoTime();
        System.out.print("\nRecursive version: ");
        for (int i = 0; i <= n; i++) {
            System.out.print(Jacobsthal_recursive(i) + ", ");
        }
        long endTime = System.nanoTime();
        long recursiveTime = (endTime - startTime) / 1000000; // Converting to milliseconds
        System.out.println("\nTime taken to execute recursive version: " + recursiveTime + " milliseconds");

        // Iterative method
        startTime = System.nanoTime();
        System.out.print("\nIterative version: ");
        for (int i = 0; i <= n; i++) {
            System.out.print(Jacobsthal_iterative(i) + ", ");
        }
        endTime = System.nanoTime();
        long iterativeTime = (endTime - startTime) / 1000000; // Converting to milliseconds
        System.out.println("\nTime taken to execute iterative version: " + iterativeTime + " milliseconds");
    }

    public static long Jacobsthal_recursive(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return Jacobsthal_recursive(n - 1) + 2 * Jacobsthal_recursive(n - 2);
    }

    public static long Jacobsthal_iterative(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        long  a = 0,  b = 1, result = 0;
        for (int i = 2; i <= n; i++) {
            result = b + 2 * a;
            	 a = b;
            	 b = result;
        }
        return result;
    }
}
