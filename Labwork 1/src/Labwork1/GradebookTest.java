package Labwork1;
import java.util.ArrayList;
import java.util.Scanner;
import practice2p.Student;

public class GradebookTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<String> prereqs = new ArrayList<>();
        prereqs.add("Programming 1");

        Course course = new Course(
                "CS101",
                "Object-Oriented Programming",
                5,
                prereqs
        );

        Gradebook gradebook = new Gradebook(course);

        System.out.print("How many students? ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("\nStudent " + (i + 1));

            System.out.print("Name: ");
            String name = scanner.next();

            System.out.print("ID: ");
            int id = scanner.nextInt();

            System.out.print("Year of study: ");
            int yos = scanner.nextInt();

            System.out.print("Grade (0–100): ");
            double grade = scanner.nextDouble();

            Student s = new Student(name, id, yos, grade);
            gradebook.addStudent(s);
        }

        System.out.println();
        gradebook.displayGradeReport();
    }
}
