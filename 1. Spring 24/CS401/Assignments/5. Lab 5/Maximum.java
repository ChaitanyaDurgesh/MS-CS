package lab5;

public class Maximum {
	
	    // Recursive method to find the maximum value in the first size elements of the array A
	    public static int maximum(int A[], int size) {
	        // Base case: if there is only one element, return it
	        if (size == 1) {
	            return A[0];
	        }
	        // Recursive call: find the maximum of the last element and the maximum of the rest of the array
	        return Math.max(A[size - 1], maximum(A, size - 1));
	    }

	    public static void main(String args[]) 
	    { int A[] = {10, -20, 1, 2, 0, 5, 100};
	        // Call the maximum method and print the result
	        int s = maximum(A, A.length);
	        System.out.println("Maximum value: " + s);
	    }
}