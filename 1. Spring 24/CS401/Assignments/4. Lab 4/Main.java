import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner;

class Worker {	//Define worker class with different ID's and Name's
    int id;  
    String name;   
    
    public Worker(int ID, String Name) { 
        this.id = ID;
        this.name = Name; 
    }
}

class Stack {
    
    private Worker[] details;
    private int top;
    
    public Stack(int size) {
        details = new Worker[size];
        top = -1;
    }
    
    public void push(Worker e) { // Method to push a Worker object
        
        if(top == details.length-1) {
            System.out.println("Stack is Full");
        }
        else {
        	details[++top] = e; 
        }
    }
    
    public Worker pop() { // Method to pop the top Worker object
        
        if(top == -1) {
            System.out.println("Stack is Empty");
            return null;
        }
        else {
            
        	Worker popped_value = details[top]; 
            top--;
            return popped_value;
        }
    }
    
    public Worker top() {	// Method to get top Worker object
        
        if(top == -1) {
            System.out.println("Stack is empty");
            return null;
        }
        else {
            return details[top];
        }
    }
}

public class Main { //Main class

    public static void main(String[] args) throws FileNotFoundException {
        
        Stack stack = new Stack(30); //Created a class with pre-defined value of 30 workers
        
        File file = new File("C:\\Users\\chait\\Desktop\\emp.txt");  //emp.txt file location to get workers details
        Scanner sc = new Scanner(file);
        
        while(sc.hasNextLine()) {
            
            String line = sc.nextLine();
            String[] parts = line.split(" "); 
            int ID = Integer.parseInt(parts[0]);
            String Name = parts[1];
            
            Worker emp = new Worker(ID, Name); //create new worker object and push into stack
            stack.push(emp); 
        }
        
        System.out.println("Top Element from Stack: " + "ID: " + stack.top().id + ", " + "Name: " + stack.top().name);
        
        stack.pop(); //first pop
        stack.pop(); //second pop
        
        System.out.println("Popping Two Elements from Stack: "); 
        System.out.println("After Popping, Top Element from Stack: " + "ID: " + stack.top().id + ", " + "Name: " + stack.top().name);
        
        Worker newEmp = new Worker(8, "Chaitanya");
        stack.push(newEmp); //pushing new worker into the stack
        
        System.out.println("After Pushing New Data, Top Element from Stack: " + "ID: " + stack.top().id + ", " + "Name: " + stack.top().name);

        sc.close();
    }

}
