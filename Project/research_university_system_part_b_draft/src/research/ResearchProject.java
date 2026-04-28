package research;

import exceptions.NotResearcherException;
import users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic;
    private final List<Researcher> participants;
    private final List<ResearchPaper> publishedPapers;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public void addParticipant(User user) throws NotResearcherException {
        if (!(user instanceof Researcher)) {
            throw new NotResearcherException("Only researchers can join research projects");
        }
        Researcher researcher = (Researcher) user;
        if (!participants.contains(researcher)) {
            participants.add(researcher);
            researcher.getResearchProfile().addProject(this);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }

    public String getTopic() {
        return topic;
    }

    public List<Researcher> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public List<ResearchPaper> getPublishedPapers() {
        return Collections.unmodifiableList(publishedPapers);
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "topic='" + topic + '\'' +
                ", participants=" + participants.size() +
                ", publishedPapers=" + publishedPapers.size() +
                '}';
    }
}
