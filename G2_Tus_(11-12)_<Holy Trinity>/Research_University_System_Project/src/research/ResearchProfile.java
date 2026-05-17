package research;

import enums.School;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearchProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private int hIndex;
    private School school;
    private final List<ResearchPaper> papers;
    private final List<ResearchProject> projects;

    public ResearchProfile(int hIndex, School school) {
        this.hIndex = hIndex;
        this.school = school;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public int getHIndex() {
        return hIndex;
    }

    public void setHIndex(int hIndex) {
        if (hIndex < 0) {
            throw new IllegalArgumentException("h-index cannot be negative");
        }
        this.hIndex = hIndex;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
        }
    }

    public void addProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
        }
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public List<ResearchProject> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    public int getTotalCitations() {
        int total = 0;
        for (ResearchPaper paper : papers) {
            total += paper.getCitations();
        }
        return total;
    }

    @Override
    public String toString() {
        return "ResearchProfile{" +
                "hIndex=" + hIndex +
                ", school=" + school +
                ", papers=" + papers.size() +
                ", projects=" + projects.size() +
                '}';
    }
}
