package academic;

import users.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private final List<Mark> marks;

    public Transcript(Student student) {
        this.student = student;
        this.marks = new ArrayList<>();
    }

    public void addMark(Mark mark) {
        if (mark != null) {
            marks.add(mark);
        }
    }

    public List<Mark> getMarks() {
        return Collections.unmodifiableList(marks);
    }

    public double calculateGpa() {
        if (marks.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Mark mark : marks) {
            total += mark.getGpaValue();
        }
        return total / marks.size();
    }

    public void printTranscript() {
        System.out.println("Transcript for " + student.getFullName());
        for (Mark mark : marks) {
            System.out.println(mark);
        }
        System.out.println("GPA: " + calculateGpa());
    }
}
