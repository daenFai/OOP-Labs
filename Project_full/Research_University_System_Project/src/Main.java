import academic.Course;
import academic.Lesson;
import academic.Mark;
import academic.RegistrationRequest;
import communication.Message;
import comparators.PaperCitationsComparator;
import comparators.PaperDateComparator;
import comparators.PaperPagesComparator;
import enums.LessonType;
import enums.ManagerType;
import enums.School;
import enums.TeacherTitle;
import exceptions.AuthenticationException;
import exceptions.CourseRegistrationException;
import exceptions.CreditLimitExceededException;
import exceptions.LowHIndexException;
import exceptions.NotResearcherException;
import facade.UniversitySystem;
import factories.UserFactory;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import reports.Report;
import storage.DataStore;
import users.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

/*
 Final console demo for the Research-Oriented University Information System.
 It demonstrates how the models, services, exceptions, serialization and design patterns work together.
 */
public class Main {
    private static final String DATA_FILE = "university_data.ser";

    public static void main(String[] args) throws Exception {
        UniversitySystem system = new UniversitySystem();
        system.getDataStore().clear();

        printHeader("1. CREATE USERS THROUGH FACTORY METHOD");

        Admin admin = UserFactory.createAdmin(
                "A001", "admin", "admin123", "System", "Admin", "admin@uni.kz",
                500000, LocalDate.of(2020, 1, 10)
        );

        Manager manager = UserFactory.createManager(
                "M001", "manager", "manager123", "Dana", "Manager", "manager@uni.kz",
                450000, LocalDate.of(2021, 2, 15), ManagerType.OR
        );

        Student student = UserFactory.createStudent(
                "S001", "student", "student123", "Balen", "Student", "student@uni.kz", 4
        );

        Student secondStudent = UserFactory.createStudent(
                "S002", "madina", "madina123", "Madina", "Student", "madina@uni.kz", 2
        );

        ResearchStudent researchStudent = UserFactory.createResearchStudent(
                "S003", "researchStudent", "rs123", "Dias", "Researcher", "dias@uni.kz",
                4, 4, School.SITE
        );

        Teacher seniorLecturer = UserFactory.createTeacher(
                "T001", "teacher", "teacher123", "Arman", "Teacher", "teacher@uni.kz",
                400000, LocalDate.of(2019, 9, 1), TeacherTitle.SENIOR_LECTOR
        );

        Professor professor = UserFactory.createProfessor(
                "P001", "prof", "prof123", "Saule", "Professor", "prof@uni.kz",
                600000, LocalDate.of(2015, 9, 1), 7, School.SITE
        );

        ResearchEmployee weakResearcher = UserFactory.createResearchEmployee(
                "R001", "weak", "weak123", "Weak", "Researcher", "weak@uni.kz",
                300000, LocalDate.of(2022, 1, 1), 2, School.SITE
        );

        ResearchEmployee businessResearcher = UserFactory.createResearchEmployee(
                "R002", "bizResearcher", "biz123", "Aruzhan", "Analyst", "aruzhan@uni.kz",
                350000, LocalDate.of(2023, 3, 1), 6, School.BS
        );

        system.addUser(admin);
        system.addUser(manager);
        system.addUser(student);
        system.addUser(secondStudent);
        system.addUser(researchStudent);
        system.addUser(seniorLecturer);
        system.addUser(professor);
        system.addUser(weakResearcher);
        system.addUser(businessResearcher);

        system.getUserService().getAllUsersSortedAlphabetically().forEach(System.out::println);

        printHeader("2. AUTHENTICATION");
        try {
            system.login("admin", "wrong-password");
        } catch (AuthenticationException e) {
            System.out.println("Expected login error: " + e.getMessage());
        }
        system.login("admin", "admin123");
        System.out.println("Logged in as: " + system.getAuthService().getCurrentUser().getFullName());

        printHeader("3. COURSE CREATION, LESSONS AND MULTIPLE INSTRUCTORS");
        Course oop = new Course("OOP101", "OOP and Design", 5, "Information Systems", 4);
        Course networks = new Course("NET201", "Computer Networks", 6, "Information Systems", 2);
        Course database = new Course("DB202", "Database Systems", 6, "Information Systems", 2);
        Course researchMethods = new Course("RES401", "Research Methods", 5, "Information Systems", 4);

        system.addCourse(oop);
        system.addCourse(networks);
        system.addCourse(database);
        system.addCourse(researchMethods);

        system.assignTeacher(oop, seniorLecturer);
        system.assignTeacher(oop, professor); // requirement: more than one instructor per course
        system.assignTeacher(researchMethods, professor);
        system.assignTeacher(networks, seniorLecturer);
        system.assignTeacher(database, seniorLecturer);

        oop.addLesson(new Lesson("Inheritance and Polymorphism", LocalDateTime.now().plusDays(1), LessonType.LECTURE, "A-101", professor));
        oop.addLesson(new Lesson("UML and Design Patterns Practice", LocalDateTime.now().plusDays(2), LessonType.PRACTICE, "A-102", seniorLecturer));
        oop.getLessons().forEach(System.out::println);

        printHeader("4. COURSE REGISTRATION WITH MANAGER APPROVAL");
        RegistrationRequest oopRequest = system.registerStudentToCourse(student, oop);
        system.approveRegistration(oopRequest);
        System.out.println("Approved: " + oopRequest);

        RegistrationRequest networksRequest = system.registerStudentToCourse(student, networks);
        system.approveRegistration(networksRequest);
        System.out.println("Approved: " + networksRequest);

        RegistrationRequest dbRequest = system.registerStudentToCourse(student, database);
        system.approveRegistration(dbRequest);
        System.out.println("Approved: " + dbRequest);

        try {
            // 5 + 6 + 6 + 5 = 22 credits, so this demonstrates the 21-credit rule.
            RegistrationRequest tooManyCredits = system.registerStudentToCourse(student, researchMethods);
            system.approveRegistration(tooManyCredits);
        } catch (CreditLimitExceededException e) {
            System.out.println("Expected credit limit exception: " + e.getMessage());
        }

        printHeader("5. TEACHER PUTS MARKS AND STUDENT VIEWS TRANSCRIPT");
        system.putMark(seniorLecturer, student, oop, new Mark(oop, 28, 27, 35));
        system.putMark(seniorLecturer, student, networks, new Mark(networks, 25, 27, 34));
        system.putMark(seniorLecturer, student, database, new Mark(database, 23, 24, 33));
        student.viewMarks();

        student.rateTeacher(seniorLecturer, 5);
        student.rateTeacher(seniorLecturer, 4);
        System.out.println("Average rating of " + seniorLecturer.getFullName() + ": " + seniorLecturer.getAverageRating());

        printHeader("6. REPORT GENERATION");
        Report report = system.generateCourseReport(oop);
        report.print();

        printHeader("7. MESSAGES AND NEWS OBSERVER PATTERN");
        Message message = professor.sendMessage(manager, "Please approve research schedule for RES401.");
        System.out.println(message);
        system.getNewsService().subscribe(student);
        system.getNewsService().subscribe(seniorLecturer);
        system.getNewsService().publishNews("Registration week", "Course registration is open until Friday.");

        printHeader("8. RESEARCH PAPERS, COMPARATORS AND PROJECTS");
        ResearchPaper paper1 = new ResearchPaper(
                "Blockchain-based Academic Records",
                "IEEE Access",
                12,
                LocalDate.of(2024, 5, 12),
                "10.1109/example.001",
                35
        );
        ResearchPaper paper2 = new ResearchPaper(
                "AI Methods in Research-Oriented Universities",
                "ACM Education Review",
                20,
                LocalDate.of(2025, 3, 20),
                "10.1145/example.002",
                60
        );
        ResearchPaper paper3 = new ResearchPaper(
                "Financial Analytics for University Management",
                "Business Informatics Journal",
                18,
                LocalDate.of(2025, 1, 15),
                "10.1000/example.003",
                72
        );

        paper1.addAuthor(professor);
        paper2.addAuthor(professor);
        paper2.addAuthor(researchStudent);
        paper3.addAuthor(businessResearcher);

        system.getResearchService().addPaper(paper1);
        system.getResearchService().addPaper(paper2);
        system.getResearchService().addPaper(paper3);

        ResearchProject project = new ResearchProject("Digital University Research Platform");
        system.getResearchService().addResearcherToProject(professor, project);
        system.getResearchService().addResearcherToProject(researchStudent, project);

        try {
            system.getResearchService().addResearcherToProject(student, project);
        } catch (NotResearcherException e) {
            System.out.println("Expected NotResearcherException: " + e.getMessage());
        }

        try {
            system.assignSupervisor(student, weakResearcher);
        } catch (LowHIndexException e) {
            System.out.println("Expected LowHIndexException: " + e.getMessage());
        }
        system.assignSupervisor(student, professor);
        System.out.println("Supervisor assigned to " + student.getFullName() + ": " + professor.getFullName());

        printPaperSorting("Professor papers sorted by citations", professor, new PaperCitationsComparator());
        printPaperSorting("Professor papers sorted by date", professor, new PaperDateComparator());
        printPaperSorting("Professor papers sorted by pages", professor, new PaperPagesComparator());

        System.out.println("\nAll papers sorted by citations:");
        system.getResearchService().printAllPapers(new PaperCitationsComparator());

        Researcher topOverall = system.getResearchService().getTopCitedResearcher();
        Researcher topSITE = system.getResearchService().getTopCitedResearcherBySchool(School.SITE);
        Researcher top2025 = system.getResearchService().getTopCitedResearcherOfYear(2025);

        System.out.println("Top cited researcher overall: " + researcherName(topOverall));
        System.out.println("Top cited researcher in SITE: " + researcherName(topSITE));
        System.out.println("Top cited researcher of 2025: " + researcherName(top2025));

        printHeader("9. SERIALIZATION");
        system.getDataStore().save(DATA_FILE);
        System.out.println("Data saved to " + DATA_FILE);
        DataStore loaded = DataStore.load(DATA_FILE);
        System.out.println("Loaded users count: " + loaded.getUsers().size());
        System.out.println("Loaded courses count: " + loaded.getCourses().size());
        System.out.println("Loaded papers count: " + loaded.getResearchPapers().size());

        printHeader("10. USER ACTION LOGS");
        system.getLogService().printLogs();

        system.logout();
    }

    private static void printHeader(String title) {
        System.out.println("\n==================== " + title + " ====================");
    }

    private static void printPaperSorting(String title, Researcher researcher, Comparator<ResearchPaper> comparator) {
        System.out.println("\n" + title + ":");
        researcher.printPapers(comparator);
    }

    private static String researcherName(Researcher researcher) {
        if (researcher instanceof User) {
            return ((User) researcher).getFullName() + " | citations=" + researcher.getResearchProfile().getTotalCitations();
        }
        return "N/A";
    }
}
