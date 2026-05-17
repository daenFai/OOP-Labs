package users;

import enums.School;
import research.ResearchProfile;
import research.Researcher;

public class ResearchStudent extends Student implements Researcher {
    private static final long serialVersionUID = 1L;

    private final ResearchProfile researchProfile;

    public ResearchStudent(String id, String username, String password,
                           String firstName, String lastName, String email,
                           int yearOfStudy, int hIndex, School school) {
        super(id, username, password, firstName, lastName, email, yearOfStudy);
        this.researchProfile = new ResearchProfile(hIndex, school);
    }

    @Override
    public ResearchProfile getResearchProfile() {
        return researchProfile;
    }
}
