package academic;

import exceptions.MarkValidationException;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private Course course;
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    public Mark(Course course, double firstAttestation, double secondAttestation, double finalExam)
            throws MarkValidationException {
        this.course = course;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
        validateMark();
    }

    public void validateMark() throws MarkValidationException {
        if (firstAttestation < 0 || firstAttestation > 30) {
            throw new MarkValidationException("First attestation must be between 0 and 30");
        }
        if (secondAttestation < 0 || secondAttestation > 30) {
            throw new MarkValidationException("Second attestation must be between 0 and 30");
        }
        if (finalExam < 0 || finalExam > 40) {
            throw new MarkValidationException("Final exam must be between 0 and 40");
        }
        if (getTotal() > 100) {
            throw new MarkValidationException("Total mark cannot exceed 100");
        }
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public String getLetterGrade() {
        double total = getTotal();
        if (total >= 95) return "A";
        if (total >= 90) return "A-";
        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "B-";
        if (total >= 70) return "C+";
        if (total >= 65) return "C";
        if (total >= 60) return "C-";
        if (total >= 55) return "D+";
        if (total >= 50) return "D";
        return "F";
    }

    public double getGpaValue() {
        double total = getTotal();
        if (total >= 95) return 4.0;
        if (total >= 90) return 3.67;
        if (total >= 85) return 3.33;
        if (total >= 80) return 3.0;
        if (total >= 75) return 2.67;
        if (total >= 70) return 2.33;
        if (total >= 65) return 2.0;
        if (total >= 60) return 1.67;
        if (total >= 55) return 1.33;
        if (total >= 50) return 1.0;
        return 0.0;
    }

    public Course getCourse() {
        return course;
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    @Override
    public String toString() {
        return course.getCode() + " | total=" + getTotal() + " | grade=" + getLetterGrade();
    }
}
