package logs;

import users.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserActionLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String action;
    private LocalDateTime createdAt;
    private User user;

    public UserActionLog(String action, User user) {
        this.action = action;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        String username = user == null ? "SYSTEM" : user.getUsername();
        return createdAt + " | " + username + " | " + action;
    }
}
