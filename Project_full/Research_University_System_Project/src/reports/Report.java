package reports;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private LocalDateTime generatedAt;

    public Report(String title, String content) {
        this.title = title;
        this.content = content;
        this.generatedAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void print() {
        System.out.println("=== " + title + " ===");
        System.out.println("Generated at: " + generatedAt);
        System.out.println(content);
    }

    @Override
    public String toString() {
        return "Report{" +
                "title='" + title + '\'' +
                ", generatedAt=" + generatedAt +
                '}';
    }
}
