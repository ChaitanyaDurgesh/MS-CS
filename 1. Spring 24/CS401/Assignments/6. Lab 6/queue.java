package Lab6;

class Employee {
    int id;
    String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + "', id=" + id + "}";
    }
}

class Queue {
    private Employee[] array; //Array to store queue elements
    private int front;
    private int rear;
    private int capacity; //Maximum capacity of the queue

    public Queue(int size) {
        capacity = size;
        array = new Employee[capacity];
        front = 0;
        rear = -1; //Initialize rear to -1 as initially queue is empty
    }

    public boolean enqueue(Employee e) {
        if (rear == capacity - 1) {
            System.out.println("Queue is full");
            return false;
        } else {
            array[++rear] = e; //Increment rear and then assign element
            return true;
        }
    }

    public Employee dequeue() {
        if (front > rear) {
            System.out.println("Queue is empty");
            return null;
        } else {
            Employee dequeued = array[front++]; //Store dequeued element and then increment front
            return dequeued;
        }
    }

    public void printQueue() {
        for (int i = front; i <= rear; i++) {
            System.out.println(array[i]);
        }
    }
}