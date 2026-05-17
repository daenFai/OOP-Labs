package users;

import academic.Course;
import academic.Mark;
import academic.RegistrationRequest;
import academic.Transcript;
import enums.UserRole;
import exceptions.CreditLimitExceededException;
import research.Researcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    public static final int MAX_CREDITS = 21;

    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private final List<Course> enrolledCourses;
    private final Transcript transcript;
    private Researcher researchSupervisor;

    public Student(String id, String username, String password,
                   String firstName, String lastName, String email,
                   int yearOfStudy) {
        super(id, username, password, firstName, lastName, email, UserRole.STUDENT);
        this.yearOfStudy = yearOfStudy;
        this.gpa = 0.0;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
        this.transcript = new Transcript(this);
    }

    public RegistrationRequest registerForCourse(Course course) {
        return new RegistrationRequest(this, course);
    }

    public void enrollInCourse(Course course) throws CreditLimitExceededException {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (totalCredits + course.getCredits() > MAX_CREDITS) {
            throw new CreditLimitExceededException("Student cannot register for more than 21 credits");
        }
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            totalCredits += course.getCredits();
        }
    }

    public void addMark(Mark mark) {
        transcript.addMark(mark);
        gpa = transcript.calculateGpa();
    }

    public void viewMarks() {
        transcript.printTranscript();
    }

    public void rateTeacher(Teacher teacher, int rating) {
        teacher.addRating(rating);
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public Researcher getResearchSupervisor() {
        return researchSupervisor;
    }

    public void setResearchSupervisor(Researcher researchSupervisor) {
        this.researchSupervisor = researchSupervisor;
    }

    @Override
    public String toString() {
        return super.toString() + " Student{" +
                "yearOfStudy=" + yearOfStudy +
                ", gpa=" + gpa +
                ", totalCredits=" + totalCredits +
                '}';
    }
}
