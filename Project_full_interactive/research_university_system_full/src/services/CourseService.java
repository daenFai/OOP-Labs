package services;

import academic.Course;
import academic.RegistrationRequest;
import enums.CourseStatus;
import enums.RegistrationStatus;
import exceptions.CourseRegistrationException;
import exceptions.CreditLimitExceededException;
import logs.UserActionLog;
import storage.DataStore;
import users.Student;
import users.Teacher;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final DataStore dataStore;

    public CourseService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addCourseForRegistration(Course course) {
        course.setStatus(CourseStatus.OPEN_FOR_REGISTRATION);
        if (!dataStore.getCourses().contains(course)) {
            dataStore.getCourses().add(course);
        }
    }

    public RegistrationRequest createRegistrationRequest(Student student, Course course)
            throws CreditLimitExceededException, CourseRegistrationException {
        if (course.getStatus() != CourseStatus.OPEN_FOR_REGISTRATION) {
            throw new CourseRegistrationException("Course is not open for registration");
        }
        if (student.getTotalCredits() + course.getCredits() > Student.MAX_CREDITS) {
            throw new CreditLimitExceededException("Student cannot register for more than 21 credits");
        }
        RegistrationRequest request = student.registerForCourse(course);
        dataStore.getRegistrationRequests().add(request);
        dataStore.getLogs().add(new UserActionLog("Created registration request for " + course.getCode(), student));
        return request;
    }

    public void approveRegistration(RegistrationRequest request)
            throws CourseRegistrationException, CreditLimitExceededException {
        if (request.getStatus() == RegistrationStatus.REJECTED) {
            throw new CourseRegistrationException("Rejected request cannot be approved");
        }
        Student student = request.getStudent();
        Course course = request.getCourse();
        student.enrollInCourse(course);
        course.addStudent(student);
        request.approve();
        dataStore.getLogs().add(new UserActionLog("Approved registration for " + course.getCode(), student));
    }

    public void rejectRegistration(RegistrationRequest request) {
        request.reject();
        dataStore.getLogs().add(new UserActionLog("Rejected registration for " + request.getCourse().getCode(), request.getStudent()));
    }

    public void assignTeacher(Course course, Teacher teacher) {
        course.addInstructor(teacher);
        teacher.addCourse(course);
    }

    public List<Course> getCoursesByStudent(Student student) {
        return new ArrayList<>(student.getEnrolledCourses());
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(dataStore.getCourses());
    }
}
