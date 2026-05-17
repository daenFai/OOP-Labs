package services;

import logs.UserActionLog;
import storage.DataStore;
import users.User;

import java.util.List;

public class LogService {
    private final DataStore dataStore;

    public LogService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void log(String action, User user) {
        dataStore.getLogs().add(new UserActionLog(action, user));
    }

    public List<UserActionLog> getLogs() {
        return dataStore.getLogs();
    }

    public void printLogs() {
        dataStore.getLogs().forEach(System.out::println);
    }
}
