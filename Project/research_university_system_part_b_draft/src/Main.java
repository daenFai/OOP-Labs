import academic.Course;
import academic.Mark;
import academic.RegistrationRequest;
import comparators.PaperCitationsComparator;
import comparators.PaperDateComparator;
import comparators.PaperPagesComparator;
import enums.ManagerType;
import enums.School;
import enums.TeacherTitle;
import exceptions.LowHIndexException;
import exceptions.NotResearcherException;
import facade.UniversitySystem;
import factories.UserFactory;
import research.ResearchPaper;
import research.ResearchProject;
import reports.Report;
import users.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        UniversitySystem system = new UniversitySystem();
        system.getDataStore().clear();

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

        Teacher teacher = UserFactory.createTeacher(
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

        system.addUser(admin);
        system.addUser(manager);
        system.addUser(student);
        system.addUser(teacher);
        system.addUser(professor);
        system.addUser(weakResearcher);

        system.login("admin", "admin123");
        System.out.println("Logged in as admin successfully.\n");

        Course oop = new Course("OOP101", "OOP and Design", 5, "Information Systems", 4);
        system.addCourse(oop);
        system.assignTeacher(oop, teacher);
        system.assignTeacher(oop, professor);

        RegistrationRequest request = system.registerStudentToCourse(student, oop);
        system.approveRegistration(request);
        System.out.println("Registration approved: " + request + "\n");

        Mark mark = new Mark(oop, 28, 27, 35);
        system.putMark(teacher, student, oop, mark);
        student.viewMarks();
        System.out.println();

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

        paper1.addAuthor(professor);
        paper2.addAuthor(professor);
        system.getResearchService().addPaper(paper1);
        system.getResearchService().addPaper(paper2);

        ResearchProject project = new ResearchProject("Digital University Research Platform");
        system.getResearchService().addResearcherToProject(professor, project);

        try {
            system.getResearchService().addResearcherToProject(student, project);
        } catch (NotResearcherException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }

        try {
            system.assignSupervisor(student, weakResearcher);
        } catch (LowHIndexException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }

        system.assignSupervisor(student, professor);
        System.out.println("Supervisor assigned: " + professor.getFullName() + "\n");

        System.out.println("Papers sorted by citations:");
        professor.printPapers(new PaperCitationsComparator());
        System.out.println("\nPapers sorted by date:");
        professor.printPapers(new PaperDateComparator());
        System.out.println("\nPapers sorted by pages:");
        professor.printPapers(new PaperPagesComparator());
        System.out.println();

        Report report = system.generateCourseReport(oop);
        report.print();

        System.out.println("\nUser action logs:");
        system.getLogService().printLogs();
    }
}
