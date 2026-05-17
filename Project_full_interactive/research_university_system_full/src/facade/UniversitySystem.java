package facade;

import academic.Course;
import academic.Mark;
import academic.RegistrationRequest;
import comparators.PaperCitationsComparator;
import exceptions.AuthenticationException;
import exceptions.CourseRegistrationException;
import exceptions.CreditLimitExceededException;
import exceptions.LowHIndexException;
import research.Researcher;
import reports.Report;
import services.*;
import storage.DataStore;
import users.Student;
import users.Teacher;
import users.User;

public class UniversitySystem {
    private final DataStore dataStore;
    private final AuthService authService;
    private final UserService userService;
    private final CourseService courseService;
    private final ResearchService researchService;
    private final ReportService reportService;
    private final NewsService newsService;
    private final LogService logService;

    public UniversitySystem() {
        this.dataStore = DataStore.getInstance();
        this.authService = new AuthService(dataStore);
        this.userService = new UserService(dataStore);
        this.courseService = new CourseService(dataStore);
        this.researchService = new ResearchService(dataStore);
        this.reportService = new ReportService();
        this.newsService = new NewsService(dataStore);
        this.logService = new LogService(dataStore);
    }

    public User login(String username, String password) throws AuthenticationException {
        return authService.login(username, password);
    }

    public void logout() {
        authService.logout();
    }

    public void addUser(User user) {
        userService.addUser(user);
    }

    public void addCourse(Course course) {
        courseService.addCourseForRegistration(course);
    }

    public RegistrationRequest registerStudentToCourse(Student student, Course course)
            throws CourseRegistrationException, CreditLimitExceededException {
        return courseService.createRegistrationRequest(student, course);
    }

    public void approveRegistration(RegistrationRequest request)
            throws CourseRegistrationException, CreditLimitExceededException {
        courseService.approveRegistration(request);
    }

    public void assignTeacher(Course course, Teacher teacher) {
        courseService.assignTeacher(course, teacher);
    }

    public void putMark(Teacher teacher, Student student, Course course, Mark mark) {
        teacher.putMark(student, course, mark);
    }

    public void assignSupervisor(Student student, Researcher researcher) throws LowHIndexException {
        researchService.assignSupervisor(student, researcher);
    }

    public void printAllResearchPapersByCitations() {
        researchService.printAllPapers(new PaperCitationsComparator());
    }

    public Report generateCourseReport(Course course) {
        return reportService.generateCoursePerformanceReport(course);
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public UserService getUserService() {
        return userService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public ResearchService getResearchService() {
        return researchService;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public LogService getLogService() {
        return logService;
    }
}
