package users;

import academic.Course;
import academic.Mark;
import enums.TeacherTitle;
import enums.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private TeacherTitle title;
    private final List<Course> courses;
    private final List<Integer> ratings;

    public Teacher(String id, String username, String password,
                   String firstName, String lastName, String email,
                   double salary, LocalDate hireDate, TeacherTitle title) {
        super(id, username, password, firstName, lastName, email, UserRole.TEACHER, salary, hireDate);
        this.title = title;
        this.courses = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    public void viewCourses() {
        courses.forEach(System.out::println);
    }

    public void manageCourse(Course course) {
        System.out.println(getFullName() + " manages course: " + course);
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (!course.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is not enrolled in this course");
        }
        student.addMark(mark);
    }

    public void viewStudents(Course course) {
        course.getStudents().forEach(System.out::println);
    }

    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return (double) sum / ratings.size();
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }
}
