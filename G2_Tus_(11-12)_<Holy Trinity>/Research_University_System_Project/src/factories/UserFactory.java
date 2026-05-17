package factories;

import enums.ManagerType;
import enums.School;
import enums.TeacherTitle;
import users.*;

import java.time.LocalDate;

/**
 * Factory Method style class for creating users in one place.
 */
public class UserFactory {
    private UserFactory() {
    }

    public static Student createStudent(String id, String username, String password,
                                        String firstName, String lastName, String email,
                                        int yearOfStudy) {
        return new Student(id, username, password, firstName, lastName, email, yearOfStudy);
    }

    public static ResearchStudent createResearchStudent(String id, String username, String password,
                                                        String firstName, String lastName, String email,
                                                        int yearOfStudy, int hIndex, School school) {
        return new ResearchStudent(id, username, password, firstName, lastName, email,
                yearOfStudy, hIndex, school);
    }

    public static Teacher createTeacher(String id, String username, String password,
                                        String firstName, String lastName, String email,
                                        double salary, LocalDate hireDate, TeacherTitle title) {
        return new Teacher(id, username, password, firstName, lastName, email,
                salary, hireDate, title);
    }

    public static Professor createProfessor(String id, String username, String password,
                                            String firstName, String lastName, String email,
                                            double salary, LocalDate hireDate,
                                            int hIndex, School school) {
        return new Professor(id, username, password, firstName, lastName, email,
                salary, hireDate, hIndex, school);
    }

    public static Manager createManager(String id, String username, String password,
                                        String firstName, String lastName, String email,
                                        double salary, LocalDate hireDate, ManagerType managerType) {
        return new Manager(id, username, password, firstName, lastName, email,
                salary, hireDate, managerType);
    }

    public static Admin createAdmin(String id, String username, String password,
                                    String firstName, String lastName, String email,
                                    double salary, LocalDate hireDate) {
        return new Admin(id, username, password, firstName, lastName, email,
                salary, hireDate);
    }

    public static ResearchEmployee createResearchEmployee(String id, String username, String password,
                                                          String firstName, String lastName, String email,
                                                          double salary, LocalDate hireDate,
                                                          int hIndex, School school) {
        return new ResearchEmployee(id, username, password, firstName, lastName, email,
                salary, hireDate, hIndex, school);
    }
}
