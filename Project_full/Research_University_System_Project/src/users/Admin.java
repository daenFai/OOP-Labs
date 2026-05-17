package users;

import enums.UserRole;
import logs.UserActionLog;
import storage.DataStore;

import java.time.LocalDate;
import java.util.List;

public class Admin extends Employee {
    private static final long serialVersionUID = 1L;

    public Admin(String id, String username, String password,
                 String firstName, String lastName, String email,
                 double salary, LocalDate hireDate) {
        super(id, username, password, firstName, lastName, email,
                UserRole.ADMIN, salary, hireDate);
    }

    public void addUser(User user) {
        DataStore.getInstance().getUsers().add(user);
        DataStore.getInstance().getLogs().add(new UserActionLog("Admin added user " + user.getUsername(), this));
    }

    public void removeUser(User user) {
        DataStore.getInstance().getUsers().remove(user);
        DataStore.getInstance().getLogs().add(new UserActionLog("Admin removed user " + user.getUsername(), this));
    }

    public void updateUser(User user) {
        DataStore.getInstance().getLogs().add(new UserActionLog("Admin updated user " + user.getUsername(), this));
    }

    public List<UserActionLog> viewActionLogs() {
        return DataStore.getInstance().getLogs();
    }
}
