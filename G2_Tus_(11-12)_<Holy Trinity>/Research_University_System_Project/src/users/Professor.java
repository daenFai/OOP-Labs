package users;

import enums.School;
import enums.TeacherTitle;
import research.ResearchProfile;
import research.Researcher;

import java.time.LocalDate;

public class Professor extends Teacher implements Researcher {
    private static final long serialVersionUID = 1L;

    private final ResearchProfile researchProfile;

    public Professor(String id, String username, String password,
                     String firstName, String lastName, String email,
                     double salary, LocalDate hireDate,
                     int hIndex, School school) {
        super(id, username, password, firstName, lastName, email,
                salary, hireDate, TeacherTitle.PROFESSOR);
        this.researchProfile = new ResearchProfile(hIndex, school);
    }

    @Override
    public ResearchProfile getResearchProfile() {
        return researchProfile;
    }
}
