package services;

import academic.Course;
import academic.Mark;
import reports.Report;
import users.Student;

public class ReportService {
    public Report generateCoursePerformanceReport(Course course) {
        double average = calculateAverageMark(course);
        Student best = findBestStudent(course);
        Student worst = findWorstStudent(course);

        String content = "Course: " + course + "\n" +
                "Students count: " + course.getStudents().size() + "\n" +
                "Average mark: " + average + "\n" +
                "Best student: " + (best == null ? "N/A" : best.getFullName()) + "\n" +
                "Worst student: " + (worst == null ? "N/A" : worst.getFullName());

        return new Report("Academic performance report", content);
    }

    public double calculateAverageMark(Course course) {
        double sum = 0.0;
        int count = 0;
        for (Student student : course.getStudents()) {
            for (Mark mark : student.getTranscript().getMarks()) {
                if (mark.getCourse().equals(course)) {
                    sum += mark.getTotal();
                    count++;
                }
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public Student findBestStudent(Course course) {
        Student best = null;
        double bestMark = -1;
        for (Student student : course.getStudents()) {
            for (Mark mark : student.getTranscript().getMarks()) {
                if (mark.getCourse().equals(course) && mark.getTotal() > bestMark) {
                    bestMark = mark.getTotal();
                    best = student;
                }
            }
        }
        return best;
    }

    public Student findWorstStudent(Course course) {
        Student worst = null;
        double worstMark = 101;
        for (Student student : course.getStudents()) {
            for (Mark mark : student.getTranscript().getMarks()) {
                if (mark.getCourse().equals(course) && mark.getTotal() < worstMark) {
                    worstMark = mark.getTotal();
                    worst = student;
                }
            }
        }
        return worst;
    }
}
