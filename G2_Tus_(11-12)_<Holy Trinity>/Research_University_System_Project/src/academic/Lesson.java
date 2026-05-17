package academic;

import enums.LessonType;
import users.Teacher;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic;
    private LocalDateTime dateTime;
    private LessonType type;
    private String room;
    private Teacher instructor;

    public Lesson(String topic, LocalDateTime dateTime, LessonType type, String room, Teacher instructor) {
        this.topic = topic;
        this.dateTime = dateTime;
        this.type = type;
        this.room = room;
        this.instructor = instructor;
    }

    public String getTopic() {
        return topic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LessonType getType() {
        return type;
    }

    public String getRoom() {
        return room;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "topic='" + topic + '\'' +
                ", dateTime=" + dateTime +
                ", type=" + type +
                ", room='" + room + '\'' +
                ", instructor=" + instructor.getFullName() +
                '}';
    }
}
