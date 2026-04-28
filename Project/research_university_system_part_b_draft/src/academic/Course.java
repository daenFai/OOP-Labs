package academic;

import enums.CourseStatus;
import users.Student;
import users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable, Comparable<Course> {
    private static final long serialVersionUID = 1L;

    private String code;
    private String courseName;
    private int credits;
    private String major;
    private int intendedYear;
    private CourseStatus status;
    private final List<Teacher> instructors;
    private final List<Student> students;
    private final List<Lesson> lessons;

    public Course(String code, String courseName, int credits, String major, int intendedYear) {
        this.code = code;
        this.courseName = courseName;
        this.credits = credits;
        this.major = major;
        this.intendedYear = intendedYear;
        this.status = CourseStatus.OPEN_FOR_REGISTRATION;
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public void addInstructor(Teacher teacher) {
        if (teacher != null && !instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    public void removeInstructor(Teacher teacher) {
        instructors.remove(teacher);
    }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public String getCode() {
        return code;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public String getMajor() {
        return major;
    }

    public int getIntendedYear() {
        return intendedYear;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public List<Teacher> getInstructors() {
        return Collections.unmodifiableList(instructors);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    @Override
    public int compareTo(Course other) {
        return this.code.compareToIgnoreCase(other.code);
    }

    @Override
    public String toString() {
        return code + " - " + courseName + " (" + credits + " credits)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
