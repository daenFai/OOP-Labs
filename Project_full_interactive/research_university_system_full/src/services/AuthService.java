package services;

import exceptions.AuthenticationException;
import logs.UserActionLog;
import storage.DataStore;
import users.User;

public class AuthService {
    private final DataStore dataStore;
    private User currentUser;

    public AuthService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public User login(String username, String password) throws AuthenticationException {
        for (User user : dataStore.getUsers()) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                currentUser = user;
                dataStore.getLogs().add(new UserActionLog("Logged in", currentUser));
                return user;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }

    public void logout() {
        if (currentUser != null) {
            dataStore.getLogs().add(new UserActionLog("Logged out", currentUser));
        }
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
