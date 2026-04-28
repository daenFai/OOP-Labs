package users;

import academic.Course;
import academic.RegistrationRequest;
import communication.News;
import enums.ManagerType;
import enums.UserRole;
import reports.Report;
import storage.DataStore;

import java.time.LocalDate;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(String id, String username, String password,
                   String firstName, String lastName, String email,
                   double salary, LocalDate hireDate, ManagerType managerType) {
        super(id, username, password, firstName, lastName, email,
                UserRole.MANAGER, salary, hireDate);
        this.managerType = managerType;
    }

    public void approveRegistration(RegistrationRequest request) {
        request.approve();
    }

    public void rejectRegistration(RegistrationRequest request) {
        request.reject();
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        course.addInstructor(teacher);
        teacher.addCourse(course);
    }

    public Report createStatisticalReport(Course course) {
        String content = "Course: " + course.getCourseName() + "\n" +
                "Students: " + course.getStudents().size() + "\n" +
                "Instructors: " + course.getInstructors().size();
        return new Report("Course statistics", content);
    }

    public News manageNews(String title, String content) {
        News news = new News(title, content);
        DataStore.getInstance().getNews().add(news);
        return news;
    }

    public ManagerType getManagerType() {
        return managerType;
    }
}
