import java.io.*;
import java.util.*;

// Student Class
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String grade;

    public Student(int id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}

// StudentManager Class
class StudentManager {
    private static final String FILE_NAME = "students.dat";
    private List<Student> students;

    public StudentManager() {
        students = loadStudents();
    }

    // Load students from file
    private List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Return an empty list if file not found
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading students: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Save students to file
    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    // Create a new student
    public void createStudent(int id, String name, String grade) {
        students.add(new Student(id, name, grade));
        saveStudents();
        System.out.println("Student added successfully.");
    }

    // Read and display all students
    public void readStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    // Update a student
    public void updateStudent(int id, String name, String grade) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(name);
                student.setGrade(grade);
                saveStudents();
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Delete a student
    public void deleteStudent(int id) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                saveStudents();
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }
        // Main method for menu-driven interaction
        public static void main(String[] args) {
            StudentManager manager = new StudentManager();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Grade: ");
                        String grade = scanner.nextLine();
                        manager.createStudent(id, name, grade);
                        break;
                    case 2:
                        manager.readStudents();
                        break;
                    case 3:
                        System.out.print("Enter ID of student to update: ");
                        id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter New Name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter New Grade: ");
                        grade = scanner.nextLine();
                        manager.updateStudent(id, name, grade);
                        break;
                    case 4:
                        System.out.print("Enter ID of student to delete: ");
                        id = scanner.nextInt();
                        manager.deleteStudent(id);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
