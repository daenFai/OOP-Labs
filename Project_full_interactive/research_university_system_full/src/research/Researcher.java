package research;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Researcher is modeled as an interface because researcher is a role/ability.
 * A student, a teacher, or another employee can be a researcher.
 */
public interface Researcher extends Serializable {
    ResearchProfile getResearchProfile();

    default void printPapers(Comparator<ResearchPaper> comparator) {
        getResearchProfile().getPapers()
                .stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }
}
