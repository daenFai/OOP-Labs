package research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static final long serialVersionUID = 1L;

    private String title;
    private final List<Researcher> authors;
    private String journal;
    private int pages;
    private LocalDate publicationDate;
    private String doi;
    private int citations;

    public ResearchPaper(String title, String journal, int pages,
                         LocalDate publicationDate, String doi, int citations) {
        this.title = title;
        this.journal = journal;
        this.pages = pages;
        this.publicationDate = publicationDate;
        this.doi = doi;
        this.citations = citations;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Researcher author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
            author.getResearchProfile().addPaper(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Researcher> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getDoi() {
        return doi;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        if (citations < 0) {
            throw new IllegalArgumentException("citations cannot be negative");
        }
        this.citations = citations;
    }

    /** Default sorting: most cited papers first. */
    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", journal='" + journal + '\'' +
                ", pages=" + pages +
                ", publicationDate=" + publicationDate +
                ", doi='" + doi + '\'' +
                ", citations=" + citations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi, title);
    }
}
