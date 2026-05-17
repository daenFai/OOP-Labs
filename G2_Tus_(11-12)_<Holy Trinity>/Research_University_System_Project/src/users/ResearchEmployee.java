package users;

import enums.School;
import enums.UserRole;
import research.ResearchProfile;
import research.Researcher;

import java.time.LocalDate;

public class ResearchEmployee extends Employee implements Researcher {
    private static final long serialVersionUID = 1L;

    private final ResearchProfile researchProfile;

    public ResearchEmployee(String id, String username, String password,
                            String firstName, String lastName, String email,
                            double salary, LocalDate hireDate,
                            int hIndex, School school) {
        super(id, username, password, firstName, lastName, email,
                UserRole.RESEARCHER, salary, hireDate);
        this.researchProfile = new ResearchProfile(hIndex, school);
    }

    @Override
    public ResearchProfile getResearchProfile() {
        return researchProfile;
    }
}
