package academic;

import enums.RegistrationStatus;
import users.Student;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private LocalDateTime createdAt;
    private RegistrationStatus status;
    private Student student;
    private Course course;

    public RegistrationRequest(Student student, Course course) {
        this.requestId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = RegistrationStatus.PENDING;
        this.student = student;
        this.course = course;
    }

    public void approve() {
        this.status = RegistrationStatus.APPROVED;
    }

    public void reject() {
        this.status = RegistrationStatus.REJECTED;
    }

    public String getRequestId() {
        return requestId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "requestId='" + requestId + '\'' +
                ", student=" + student.getFullName() +
                ", course=" + course.getCode() +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
