package users;

import communication.Message;
import enums.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Employee extends User {
    private static final long serialVersionUID = 1L;

    private double salary;
    private LocalDate hireDate;
    private final List<Message> messages;

    protected Employee(String id, String username, String password,
                       String firstName, String lastName, String email,
                       UserRole role, double salary, LocalDate hireDate) {
        super(id, username, password, firstName, lastName, email, role);
        this.salary = salary;
        this.hireDate = hireDate;
        this.messages = new ArrayList<>();
    }

    public void sendMessage(Employee receiver, Message message) {
        if (receiver == null || message == null) {
            throw new IllegalArgumentException("Receiver and message cannot be null");
        }
        receiver.receiveMessage(message);
    }

    public Message sendMessage(Employee receiver, String text) {
        Message message = new Message(text, this, receiver);
        sendMessage(receiver, message);
        return message;
    }

    public void receiveMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }
}
