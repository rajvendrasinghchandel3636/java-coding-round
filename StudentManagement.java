import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    static final String URL = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root"; // Apne MySQL ka username yaha likhein
    static final String PASSWORD = ""; // Apna MySQL password yaha likhein

    public static void main(String[] args) {
        try (
            
        Class.forName("com.mysql.cj.jdbc.Driver");
            
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            
            while (true) {
                System.out.println("\n1. Add Student\n2. Show Students\n3. Update Student\n4. Delete Student\n5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: addStudent(conn, scanner); break;
                    case 2: showStudents(conn); break;
                    case 3: updateStudent(conn, scanner); break;
                    case 4: deleteStudent(conn, scanner); break;
                    case 5: return;
                    default: System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        
        String sql = "INSERT INTO students (name, age) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.executeUpdate();
            System.out.println("Student added successfully!");
        }
    }

    static void showStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("age"));
            }
        }
    }

    static void updateStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        
        String sql = "UPDATE students SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Student updated successfully!");
        }
    }

    static void deleteStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Student deleted successfully!");
        }
    }
}