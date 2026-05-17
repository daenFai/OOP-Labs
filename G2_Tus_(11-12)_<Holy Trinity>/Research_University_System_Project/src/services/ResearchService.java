package services;

import enums.School;
import exceptions.LowHIndexException;
import exceptions.NotResearcherException;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import storage.DataStore;
import users.Student;
import users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResearchService {
    private final DataStore dataStore;

    public ResearchService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void assignSupervisor(Student student, Researcher supervisor) throws LowHIndexException {
        if (student.getYearOfStudy() != 4) {
            throw new IllegalArgumentException("Only 4th year students can have research supervisors");
        }
        if (supervisor.getResearchProfile().getHIndex() < 3) {
            throw new LowHIndexException("Supervisor must have h-index at least 3");
        }
        student.setResearchSupervisor(supervisor);
    }

    public void addResearcherToProject(User user, ResearchProject project) throws NotResearcherException {
        project.addParticipant(user);
        if (!dataStore.getResearchProjects().contains(project)) {
            dataStore.getResearchProjects().add(project);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (!dataStore.getResearchPapers().contains(paper)) {
            dataStore.getResearchPapers().add(paper);
        }
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        dataStore.getResearchPapers()
                .stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public Researcher getTopCitedResearcher() {
        Researcher best = null;
        int maxCitations = -1;
        for (User user : dataStore.getUsers()) {
            if (user instanceof Researcher) {
                Researcher researcher = (Researcher) user;
                int citations = researcher.getResearchProfile().getTotalCitations();
                if (citations > maxCitations) {
                    maxCitations = citations;
                    best = researcher;
                }
            }
        }
        return best;
    }

    public Researcher getTopCitedResearcherBySchool(School school) {
        Researcher best = null;
        int maxCitations = -1;
        for (User user : dataStore.getUsers()) {
            if (user instanceof Researcher) {
                Researcher researcher = (Researcher) user;
                if (researcher.getResearchProfile().getSchool() == school) {
                    int citations = researcher.getResearchProfile().getTotalCitations();
                    if (citations > maxCitations) {
                        maxCitations = citations;
                        best = researcher;
                    }
                }
            }
        }
        return best;
    }

    public Researcher getTopCitedResearcherOfYear(int year) {
        Researcher best = null;
        int maxCitations = -1;
        for (User user : dataStore.getUsers()) {
            if (user instanceof Researcher) {
                Researcher researcher = (Researcher) user;
                int citations = 0;
                for (ResearchPaper paper : researcher.getResearchProfile().getPapers()) {
                    if (paper.getPublicationDate().getYear() == year) {
                        citations += paper.getCitations();
                    }
                }
                if (citations > maxCitations) {
                    maxCitations = citations;
                    best = researcher;
                }
            }
        }
        return best;
    }

    public List<Researcher> getAllResearchers() {
        List<Researcher> researchers = new ArrayList<>();
        for (User user : dataStore.getUsers()) {
            if (user instanceof Researcher) {
                researchers.add((Researcher) user);
            }
        }
        return researchers;
    }
}
