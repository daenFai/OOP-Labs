package communication;

import users.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private LocalDateTime sentAt;
    private Employee sender;
    private Employee receiver;

    public Message(String text, Employee sender, Employee receiver) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.sentAt = LocalDateTime.now();
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", sentAt=" + sentAt +
                ", sender=" + sender.getFullName() +
                ", receiver=" + receiver.getFullName() +
                '}';
    }
}
