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
import exceptions.MarkValidationException;
import exceptions.NotResearcherException;
import exceptions.UserNotFoundException;
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
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Research-Oriented University Information System.
 *
 * The program has two modes:
 * 1) automatic demo mode for quick defense presentation;
 * 2) interactive console menu where users log in and choose actions manually.
 */
public class Main {
    private static final String DATA_FILE = "university_data.ser";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printHeader("RESEARCH-ORIENTED UNIVERSITY SYSTEM");
            System.out.println("1. Run automatic demo");
            System.out.println("2. Open interactive console menu");
            System.out.println("0. Exit");
            int choice = readInt(scanner, "Choose mode: ");

            try {
                if (choice == 1) {
                    runAutomaticDemo();
                } else if (choice == 2) {
                    runInteractiveMenu(scanner);
                } else if (choice == 0) {
                    System.out.println("Goodbye!");
                    return;
                } else {
                    System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Program error: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
    }

    /*
    Full automatic scenario. This is useful for defense because it quickly shows
    all important requirements: authentication, registration, marks, research,
    exceptions, reports, logs, and serialization.
     */
    private static void runAutomaticDemo() throws Exception {
        UniversitySystem system = new UniversitySystem();
        DemoData data = createBaseData(system);

        printHeader("1. USERS CREATED THROUGH FACTORY METHOD");
        system.getUserService().getAllUsersSortedAlphabetically().forEach(System.out::println);

        printHeader("2. AUTHENTICATION");
        try {
            system.login("admin", "wrong-password");
        } catch (AuthenticationException e) {
            System.out.println("Expected login error: " + e.getMessage());
        }
        system.login("admin", "admin123");
        System.out.println("Logged in as: " + system.getAuthService().getCurrentUser().getFullName());

        printHeader("3. COURSES, LESSONS AND MULTIPLE INSTRUCTORS");
        data.oop.getLessons().forEach(System.out::println);
        System.out.println("OOP instructors:");
        data.oop.getInstructors().forEach(System.out::println);

        printHeader("4. COURSE REGISTRATION WITH MANAGER APPROVAL");
        RegistrationRequest oopRequest = system.registerStudentToCourse(data.student, data.oop);
        system.approveRegistration(oopRequest);
        System.out.println("Approved: " + oopRequest);

        RegistrationRequest networksRequest = system.registerStudentToCourse(data.student, data.networks);
        system.approveRegistration(networksRequest);
        System.out.println("Approved: " + networksRequest);

        RegistrationRequest dbRequest = system.registerStudentToCourse(data.student, data.database);
        system.approveRegistration(dbRequest);
        System.out.println("Approved: " + dbRequest);

        try {
            // 5 + 6 + 6 + 5 = 22 credits, so this demonstrates the 21-credit rule.
            RegistrationRequest tooManyCredits = system.registerStudentToCourse(data.student, data.researchMethods);
            system.approveRegistration(tooManyCredits);
        } catch (CreditLimitExceededException e) {
            System.out.println("Expected credit limit exception: " + e.getMessage());
        }

        printHeader("5. TEACHER PUTS MARKS AND STUDENT VIEWS TRANSCRIPT");
        system.putMark(data.seniorLecturer, data.student, data.oop, new Mark(data.oop, 28, 27, 35));
        system.putMark(data.seniorLecturer, data.student, data.networks, new Mark(data.networks, 25, 27, 34));
        system.putMark(data.seniorLecturer, data.student, data.database, new Mark(data.database, 23, 24, 33));
        data.student.viewMarks();

        data.student.rateTeacher(data.seniorLecturer, 5);
        data.student.rateTeacher(data.seniorLecturer, 4);
        System.out.println("Average rating of " + data.seniorLecturer.getFullName() + ": " + data.seniorLecturer.getAverageRating());

        printHeader("6. REPORT GENERATION");
        Report report = system.generateCourseReport(data.oop);
        report.print();

        printHeader("7. MESSAGES AND NEWS OBSERVER PATTERN");
        Message message = data.professor.sendMessage(data.manager, "Please approve research schedule for RES401.");
        System.out.println(message);
        system.getNewsService().subscribe(data.student);
        system.getNewsService().subscribe(data.seniorLecturer);
        system.getNewsService().publishNews("Registration week", "Course registration is open until Friday.");

        printHeader("8. RESEARCH PAPERS, COMPARATORS AND PROJECTS");
        system.getResearchService().addResearcherToProject(data.professor, data.digitalProject);
        system.getResearchService().addResearcherToProject(data.researchStudent, data.digitalProject);

        try {
            system.getResearchService().addResearcherToProject(data.student, data.digitalProject);
        } catch (NotResearcherException e) {
            System.out.println("Expected NotResearcherException: " + e.getMessage());
        }

        try {
            system.assignSupervisor(data.student, data.weakResearcher);
        } catch (LowHIndexException e) {
            System.out.println("Expected LowHIndexException: " + e.getMessage());
        }
        system.assignSupervisor(data.student, data.professor);
        System.out.println("Supervisor assigned to " + data.student.getFullName() + ": " + data.professor.getFullName());

        printPaperSorting("Professor papers sorted by citations", data.professor, new PaperCitationsComparator());
        printPaperSorting("Professor papers sorted by date", data.professor, new PaperDateComparator());
        printPaperSorting("Professor papers sorted by pages", data.professor, new PaperPagesComparator());

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

    /*
    Interactive menu for intuitive usage. It uses the same classes and services,
    but user chooses actions manually through Scanner input.
     */
    private static void runInteractiveMenu(Scanner scanner) throws Exception {
        UniversitySystem system = new UniversitySystem();
        createBaseData(system);

        printHeader("INTERACTIVE MODE");
        printCredentials();

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("0. Back to main menu");
            int choice = readInt(scanner, "Choose option: ");
            if (choice == 0) {
                return;
            }
            if (choice != 1) {
                System.out.println("Unknown option.");
                continue;
            }

            String username = readLine(scanner, "Username: ");
            String password = readLine(scanner, "Password: ");

            try {
                User currentUser = system.login(username, password);
                System.out.println("Logged in as: " + currentUser.getFullName() + " [" + currentUser.getRole() + "]");
                openRoleMenu(scanner, system, currentUser);
                system.logout();
            } catch (AuthenticationException e) {
                System.out.println("Login failed: " + e.getMessage());
            }
        }
    }

    private static void openRoleMenu(Scanner scanner, UniversitySystem system, User user) {
        if (user instanceof Student) {
            studentMenu(scanner, system, (Student) user);
        } else if (user instanceof Teacher) {
            teacherMenu(scanner, system, (Teacher) user);
        } else if (user instanceof Manager) {
            managerMenu(scanner, system, (Manager) user);
        } else if (user instanceof Admin) {
            adminMenu(scanner, system, (Admin) user);
        } else if (user instanceof Researcher) {
            researcherMenu(scanner, system, (Researcher) user);
        } else {
            user.viewProfile();
        }
    }

    private static void studentMenu(Scanner scanner, UniversitySystem system, Student student) {
        while (true) {
            printHeader("STUDENT MENU");
            System.out.println("1. View profile");
            System.out.println("2. View all courses");
            System.out.println("3. Register for course");
            System.out.println("4. View my courses");
            System.out.println("5. View marks/transcript");
            System.out.println("6. Rate teacher");
            System.out.println("7. View research supervisor");
            if (student instanceof Researcher) {
                System.out.println("8. Research options");
            }
            System.out.println("0. Logout");

            int choice = readInt(scanner, "Choose option: ");
            try {
                switch (choice) {
                    case 1:
                        student.viewProfile();
                        break;
                    case 2:
                        printCourses(system.getDataStore().getCourses());
                        break;
                    case 3:
                        registerForCourse(scanner, system, student);
                        break;
                    case 4:
                        printCourses(student.getEnrolledCourses());
                        break;
                    case 5:
                        student.viewMarks();
                        break;
                    case 6:
                        rateTeacher(scanner, system, student);
                        break;
                    case 7:
                        Researcher supervisor = student.getResearchSupervisor();
                        System.out.println(supervisor == null ? "No supervisor assigned." : researcherName(supervisor));
                        break;
                    case 8:
                        if (student instanceof Researcher) {
                            researcherMenu(scanner, system, (Researcher) student);
                        } else {
                            System.out.println("Unknown option.");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void teacherMenu(Scanner scanner, UniversitySystem system, Teacher teacher) {
        while (true) {
            printHeader("TEACHER MENU");
            System.out.println("1. View profile");
            System.out.println("2. View my courses");
            System.out.println("3. View students by course code");
            System.out.println("4. Put mark");
            System.out.println("5. Send message to manager");
            if (teacher instanceof Researcher) {
                System.out.println("6. Research options");
            }
            System.out.println("0. Logout");

            int choice = readInt(scanner, "Choose option: ");
            try {
                switch (choice) {
                    case 1:
                        teacher.viewProfile();
                        break;
                    case 2:
                        printCourses(teacher.getCourses());
                        break;
                    case 3:
                        Course course = chooseCourse(scanner, system);
                        course.getStudents().forEach(System.out::println);
                        break;
                    case 4:
                        putMark(scanner, system, teacher);
                        break;
                    case 5:
                        sendMessageToManager(scanner, system, teacher);
                        break;
                    case 6:
                        if (teacher instanceof Researcher) {
                            researcherMenu(scanner, system, (Researcher) teacher);
                        } else {
                            System.out.println("Unknown option.");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void managerMenu(Scanner scanner, UniversitySystem system, Manager manager) {
        while (true) {
            printHeader("MANAGER MENU");
            System.out.println("1. View profile");
            System.out.println("2. View registration requests");
            System.out.println("3. Approve request");
            System.out.println("4. Reject request");
            System.out.println("5. Assign teacher to course");
            System.out.println("6. Generate course report");
            System.out.println("7. Publish news");
            System.out.println("8. View users sorted alphabetically");
            System.out.println("0. Logout");

            int choice = readInt(scanner, "Choose option: ");
            try {
                switch (choice) {
                    case 1:
                        manager.viewProfile();
                        break;
                    case 2:
                        printRegistrationRequests(system.getDataStore().getRegistrationRequests());
                        break;
                    case 3:
                        approveRequest(scanner, system);
                        break;
                    case 4:
                        rejectRequest(scanner, system);
                        break;
                    case 5:
                        assignTeacher(scanner, system);
                        break;
                    case 6:
                        Course course = chooseCourse(scanner, system);
                        system.generateCourseReport(course).print();
                        break;
                    case 7:
                        String title = readLine(scanner, "News title: ");
                        String content = readLine(scanner, "News content: ");
                        system.getNewsService().publishNews(title, content);
                        System.out.println("News published.");
                        break;
                    case 8:
                        system.getUserService().getAllUsersSortedAlphabetically().forEach(System.out::println);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void adminMenu(Scanner scanner, UniversitySystem system, Admin admin) {
        while (true) {
            printHeader("ADMIN MENU");
            System.out.println("1. View profile");
            System.out.println("2. View all users");
            System.out.println("3. Add sample student");
            System.out.println("4. Remove user by id");
            System.out.println("5. View user action logs");
            System.out.println("0. Logout");

            int choice = readInt(scanner, "Choose option: ");
            try {
                switch (choice) {
                    case 1:
                        admin.viewProfile();
                        break;
                    case 2:
                        system.getUserService().getAllUsersSortedAlphabetically().forEach(System.out::println);
                        break;
                    case 3:
                        addSampleStudent(scanner, system);
                        break;
                    case 4:
                        String userId = readLine(scanner, "Enter user id: ");
                        system.getUserService().removeUser(userId);
                        System.out.println("User removed.");
                        break;
                    case 5:
                        system.getLogService().printLogs();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void researcherMenu(Scanner scanner, UniversitySystem system, Researcher researcher) {
        while (true) {
            printHeader("RESEARCHER MENU");
            System.out.println("1. View research profile");
            System.out.println("2. Print my papers by citations");
            System.out.println("3. Print my papers by date");
            System.out.println("4. Print my papers by pages");
            System.out.println("5. Add research paper");
            System.out.println("6. Join demo research project");
            System.out.println("7. View top-cited researcher");
            System.out.println("0. Back");

            int choice = readInt(scanner, "Choose option: ");
            try {
                switch (choice) {
                    case 1:
                        System.out.println(researcher.getResearchProfile());
                        break;
                    case 2:
                        researcher.printPapers(new PaperCitationsComparator());
                        break;
                    case 3:
                        researcher.printPapers(new PaperDateComparator());
                        break;
                    case 4:
                        researcher.printPapers(new PaperPagesComparator());
                        break;
                    case 5:
                        addResearchPaper(scanner, system, researcher);
                        break;
                    case 6:
                        ResearchProject project = getOrCreateDemoProject(system);
                        if (researcher instanceof User) {
                            system.getResearchService().addResearcherToProject((User) researcher, project);
                            System.out.println("Joined project: " + project.getTopic());
                        }
                        break;
                    case 7:
                        System.out.println("Top cited researcher: " + researcherName(system.getResearchService().getTopCitedResearcher()));
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static DemoData createBaseData(UniversitySystem system) throws MarkValidationException {
        system.getDataStore().clear();

        DemoData data = new DemoData();

        data.admin = UserFactory.createAdmin(
                "A001", "admin", "admin123", "System", "Admin", "admin@uni.kz",
                500000, LocalDate.of(2020, 1, 10)
        );
        data.manager = UserFactory.createManager(
                "M001", "manager", "manager123", "Dana", "Manager", "manager@uni.kz",
                450000, LocalDate.of(2021, 2, 15), ManagerType.OR
        );
        data.student = UserFactory.createStudent(
                "S001", "student", "student123", "Aibyn", "Student", "student@uni.kz", 4
        );
        data.secondStudent = UserFactory.createStudent(
                "S002", "madina", "madina123", "Madina", "Student", "madina@uni.kz", 2
        );
        data.researchStudent = UserFactory.createResearchStudent(
                "S003", "researchStudent", "rs123", "Dias", "Researcher", "dias@uni.kz",
                4, 4, School.SITE
        );
        data.seniorLecturer = UserFactory.createTeacher(
                "T001", "teacher", "teacher123", "Arman", "Teacher", "teacher@uni.kz",
                400000, LocalDate.of(2019, 9, 1), TeacherTitle.SENIOR_LECTOR
        );
        data.professor = UserFactory.createProfessor(
                "P001", "prof", "prof123", "Saule", "Professor", "prof@uni.kz",
                600000, LocalDate.of(2015, 9, 1), 7, School.SITE
        );
        data.weakResearcher = UserFactory.createResearchEmployee(
                "R001", "weak", "weak123", "Weak", "Researcher", "weak@uni.kz",
                300000, LocalDate.of(2022, 1, 1), 2, School.SITE
        );
        data.businessResearcher = UserFactory.createResearchEmployee(
                "R002", "bizResearcher", "biz123", "Aruzhan", "Analyst", "aruzhan@uni.kz",
                350000, LocalDate.of(2023, 3, 1), 6, School.BS
        );

        system.addUser(data.admin);
        system.addUser(data.manager);
        system.addUser(data.student);
        system.addUser(data.secondStudent);
        system.addUser(data.researchStudent);
        system.addUser(data.seniorLecturer);
        system.addUser(data.professor);
        system.addUser(data.weakResearcher);
        system.addUser(data.businessResearcher);

        data.oop = new Course("OOP101", "OOP and Design", 5, "Information Systems", 4);
        data.networks = new Course("NET201", "Computer Networks", 6, "Information Systems", 2);
        data.database = new Course("DB202", "Database Systems", 6, "Information Systems", 2);
        data.researchMethods = new Course("RES401", "Research Methods", 5, "Information Systems", 4);

        system.addCourse(data.oop);
        system.addCourse(data.networks);
        system.addCourse(data.database);
        system.addCourse(data.researchMethods);

        system.assignTeacher(data.oop, data.seniorLecturer);
        system.assignTeacher(data.oop, data.professor);
        system.assignTeacher(data.researchMethods, data.professor);
        system.assignTeacher(data.networks, data.seniorLecturer);
        system.assignTeacher(data.database, data.seniorLecturer);

        data.oop.addLesson(new Lesson("Inheritance and Polymorphism", LocalDateTime.now().plusDays(1), LessonType.LECTURE, "A-101", data.professor));
        data.oop.addLesson(new Lesson("UML and Design Patterns Practice", LocalDateTime.now().plusDays(2), LessonType.PRACTICE, "A-102", data.seniorLecturer));

        data.paper1 = new ResearchPaper("Blockchain-based Academic Records", "IEEE Access", 12,
                LocalDate.of(2024, 5, 12), "10.1109/example.001", 35);
        data.paper2 = new ResearchPaper("AI Methods in Research-Oriented Universities", "ACM Education Review", 20,
                LocalDate.of(2025, 3, 20), "10.1145/example.002", 60);
        data.paper3 = new ResearchPaper("Financial Analytics for University Management", "Business Informatics Journal", 18,
                LocalDate.of(2025, 1, 15), "10.1000/example.003", 72);

        data.paper1.addAuthor(data.professor);
        data.paper2.addAuthor(data.professor);
        data.paper2.addAuthor(data.researchStudent);
        data.paper3.addAuthor(data.businessResearcher);

        system.getResearchService().addPaper(data.paper1);
        system.getResearchService().addPaper(data.paper2);
        system.getResearchService().addPaper(data.paper3);

        data.digitalProject = new ResearchProject("Digital University Research Platform");
        system.getDataStore().getResearchProjects().add(data.digitalProject);

        system.getNewsService().subscribe(data.student);
        system.getNewsService().subscribe(data.secondStudent);
        system.getNewsService().subscribe(data.seniorLecturer);

        return data;
    }

    private static void registerForCourse(Scanner scanner, UniversitySystem system, Student student)
            throws CourseRegistrationException, CreditLimitExceededException {
        Course course = chooseCourse(scanner, system);
        RegistrationRequest request = system.registerStudentToCourse(student, course);
        System.out.println("Request created: " + request);
        System.out.println("Status is PENDING. Manager must approve it.");
    }

    private static void rateTeacher(Scanner scanner, UniversitySystem system, Student student) throws UserNotFoundException {
        Teacher teacher = chooseTeacher(scanner, system);
        int rating = readInt(scanner, "Rating from 1 to 5: ");
        student.rateTeacher(teacher, rating);
        System.out.println("Rating added. New average: " + teacher.getAverageRating());
    }

    private static void putMark(Scanner scanner, UniversitySystem system, Teacher teacher)
            throws UserNotFoundException, MarkValidationException {
        Course course = chooseCourse(scanner, system);
        Student student = chooseStudent(scanner, system);
        double att1 = readDouble(scanner, "First attestation (0-30): ");
        double att2 = readDouble(scanner, "Second attestation (0-30): ");
        double fin = readDouble(scanner, "Final exam (0-40): ");
        system.putMark(teacher, student, course, new Mark(course, att1, att2, fin));
        System.out.println("Mark added.");
    }

    private static void sendMessageToManager(Scanner scanner, UniversitySystem system, Teacher teacher) {
        Manager manager = null;
        for (User user : system.getDataStore().getUsers()) {
            if (user instanceof Manager) {
                manager = (Manager) user;
                break;
            }
        }
        if (manager == null) {
            System.out.println("No manager found.");
            return;
        }
        String text = readLine(scanner, "Message text: ");
        Message message = teacher.sendMessage(manager, text);
        System.out.println("Sent: " + message);
    }

    private static void approveRequest(Scanner scanner, UniversitySystem system)
            throws CourseRegistrationException, CreditLimitExceededException {
        List<RegistrationRequest> requests = system.getDataStore().getRegistrationRequests();
        printRegistrationRequests(requests);
        int index = readInt(scanner, "Request number to approve: ") - 1;
        if (index < 0 || index >= requests.size()) {
            System.out.println("Invalid request number.");
            return;
        }
        system.approveRegistration(requests.get(index));
        System.out.println("Request approved.");
    }

    private static void rejectRequest(Scanner scanner, UniversitySystem system) {
        List<RegistrationRequest> requests = system.getDataStore().getRegistrationRequests();
        printRegistrationRequests(requests);
        int index = readInt(scanner, "Request number to reject: ") - 1;
        if (index < 0 || index >= requests.size()) {
            System.out.println("Invalid request number.");
            return;
        }
        system.getCourseService().rejectRegistration(requests.get(index));
        System.out.println("Request rejected.");
    }

    private static void assignTeacher(Scanner scanner, UniversitySystem system) throws UserNotFoundException {
        Course course = chooseCourse(scanner, system);
        Teacher teacher = chooseTeacher(scanner, system);
        system.assignTeacher(course, teacher);
        System.out.println("Teacher assigned to course.");
    }

    private static void addSampleStudent(Scanner scanner, UniversitySystem system) {
        String id = readLine(scanner, "New student id: ");
        String username = readLine(scanner, "Username: ");
        String firstName = readLine(scanner, "First name: ");
        String lastName = readLine(scanner, "Last name: ");
        int year = readInt(scanner, "Year of study: ");
        Student newStudent = UserFactory.createStudent(id, username, "12345", firstName, lastName,
                username + "@uni.kz", year);
        system.addUser(newStudent);
        System.out.println("Student added. Default password: 12345");
    }

    private static void addResearchPaper(Scanner scanner, UniversitySystem system, Researcher researcher) {
        String title = readLine(scanner, "Paper title: ");
        String journal = readLine(scanner, "Journal: ");
        int pages = readInt(scanner, "Pages: ");
        int citations = readInt(scanner, "Citations: ");
        String doi = "10.demo/" + System.currentTimeMillis();
        ResearchPaper paper = new ResearchPaper(title, journal, pages, LocalDate.now(), doi, citations);
        paper.addAuthor(researcher);
        system.getResearchService().addPaper(paper);
        System.out.println("Research paper added: " + paper);
    }

    private static ResearchProject getOrCreateDemoProject(UniversitySystem system) {
        if (!system.getDataStore().getResearchProjects().isEmpty()) {
            return system.getDataStore().getResearchProjects().get(0);
        }
        ResearchProject project = new ResearchProject("Interactive Research Project");
        system.getDataStore().getResearchProjects().add(project);
        return project;
    }

    private static Course chooseCourse(Scanner scanner, UniversitySystem system) {
        printCourses(system.getDataStore().getCourses());
        String code = readLine(scanner, "Course code: ");
        Course course = findCourseByCode(system, code);
        if (course == null) {
            throw new IllegalArgumentException("Course with code " + code + " was not found");
        }
        return course;
    }

    private static Student chooseStudent(Scanner scanner, UniversitySystem system) throws UserNotFoundException {
        System.out.println("Students:");
        for (User user : system.getDataStore().getUsers()) {
            if (user instanceof Student) {
                System.out.println(user.getId() + " - " + user.getFullName());
            }
        }
        String id = readLine(scanner, "Student id: ");
        User user = system.getUserService().findUserById(id);
        if (!(user instanceof Student)) {
            throw new IllegalArgumentException("User is not a student");
        }
        return (Student) user;
    }

    private static Teacher chooseTeacher(Scanner scanner, UniversitySystem system) throws UserNotFoundException {
        System.out.println("Teachers:");
        for (User user : system.getDataStore().getUsers()) {
            if (user instanceof Teacher) {
                System.out.println(user.getId() + " - " + user.getFullName());
            }
        }
        String id = readLine(scanner, "Teacher id: ");
        User user = system.getUserService().findUserById(id);
        if (!(user instanceof Teacher)) {
            throw new IllegalArgumentException("User is not a teacher");
        }
        return (Teacher) user;
    }

    private static Course findCourseByCode(UniversitySystem system, String code) {
        for (Course course : system.getDataStore().getCourses()) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private static void printCourses(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("No courses.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void printRegistrationRequests(List<RegistrationRequest> requests) {
        if (requests.isEmpty()) {
            System.out.println("No registration requests.");
            return;
        }
        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + ". " + requests.get(i));
        }
    }

    private static void printCredentials() {
        System.out.println("Demo accounts:");
        System.out.println("admin / admin123");
        System.out.println("manager / manager123");
        System.out.println("student / student123");
        System.out.println("teacher / teacher123");
        System.out.println("prof / prof123");
        System.out.println("researchStudent / rs123");
        System.out.println("weak / weak123");
        System.out.println("bizResearcher / biz123");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
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

    private static class DemoData {
        Admin admin;
        Manager manager;
        Student student;
        Student secondStudent;
        ResearchStudent researchStudent;
        Teacher seniorLecturer;
        Professor professor;
        ResearchEmployee weakResearcher;
        ResearchEmployee businessResearcher;
        Course oop;
        Course networks;
        Course database;
        Course researchMethods;
        ResearchPaper paper1;
        ResearchPaper paper2;
        ResearchPaper paper3;
        ResearchProject digitalProject;
    }
}
