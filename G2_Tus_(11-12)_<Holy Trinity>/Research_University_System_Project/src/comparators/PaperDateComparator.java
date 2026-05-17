package comparators;

import research.ResearchPaper;

import java.util.Comparator;

public class PaperDateComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        return p2.getPublicationDate().compareTo(p1.getPublicationDate());
    }
}
