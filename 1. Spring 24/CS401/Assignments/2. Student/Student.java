import java.util.Scanner;

// Student class
class Student implements Comparable<Student> {
    private int id;
    private String Name;

    public Student(int id, String Name) {
        this.id = id;
        this.Name = Name;
    }

    // Using Getters and Setters
    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" + "Id=" + id + ", Name='" + Name + '\'' + '}';
    }

    // CompareTo method for sorting students based of names
    @Override
    public int compareTo(Student other) {
        // Compare based on Names first
        int nameComparison = this.Name.compareTo(other.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }

        // If names are same, comparing based on IDs
        return Integer.compare(this.id, other.getid());
    }
}


// Main class
class Main {
    public static void main(String[] args) {
        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Input for first student
        System.out.println("Enter details for the first student:");
        System.out.print("Name: ");
        String name1 = scanner.nextLine();
        System.out.print("ID: ");
        int id1 = scanner.nextInt();
        scanner.nextLine(); 

        // Input for second student
        System.out.println("\nEnter details for the second student:");
        System.out.print("Name: ");
        String name2 = scanner.nextLine();
        System.out.print("ID: ");
        int id2 = scanner.nextInt();

        // Creating instances of Student
        Student student1 = new Student(id1, name1);
        Student student2 = new Student(id2, name2);

        // Displaying input students
        System.out.println("\n Input Students:");
        System.out.println(student1);
        System.out.println(student2);

        // Comparing input students
        int comparisonResult = student1.compareTo(student2);


     // Displaying comparison, with student names
     System.out.println("\nComparison Result:");
     if (comparisonResult < 0) {
         System.out.println(student1.getName() + " comes before " + student2.getName() + ".");
     } else if (comparisonResult > 0) {
         System.out.println(student2.getName() + " comes before " + student1.getName() + ".");
     } else {
         System.out.println("Both students (" + student1.getName() + ") are equal.");
     }

        // Close scanner
        scanner.close();
    }
}
