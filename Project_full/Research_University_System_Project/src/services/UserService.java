package services;

import exceptions.UserNotFoundException;
import logs.UserActionLog;
import storage.DataStore;
import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserService {
    private final DataStore dataStore;

    public UserService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addUser(User user) {
        if (!dataStore.getUsers().contains(user)) {
            dataStore.getUsers().add(user);
            dataStore.getLogs().add(new UserActionLog("User added: " + user.getUsername(), user));
        }
    }

    public void removeUser(String userId) throws UserNotFoundException {
        User user = findUserById(userId);
        dataStore.getUsers().remove(user);
        dataStore.getLogs().add(new UserActionLog("User removed: " + user.getUsername(), user));
    }

    public void updateUser(User updatedUser) throws UserNotFoundException {
        User existingUser = findUserById(updatedUser.getId());
        int index = dataStore.getUsers().indexOf(existingUser);
        dataStore.getUsers().set(index, updatedUser);
        dataStore.getLogs().add(new UserActionLog("User updated: " + updatedUser.getUsername(), updatedUser));
    }

    public User findUserById(String userId) throws UserNotFoundException {
        for (User user : dataStore.getUsers()) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        throw new UserNotFoundException("User with id " + userId + " was not found");
    }

    public List<User> getAllUsersSortedAlphabetically() {
        List<User> copy = new ArrayList<>(dataStore.getUsers());
        Collections.sort(copy);
        return copy;
    }
}
