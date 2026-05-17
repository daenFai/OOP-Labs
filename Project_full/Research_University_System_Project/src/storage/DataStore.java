package storage;

import academic.Course;
import academic.RegistrationRequest;
import communication.News;
import logs.UserActionLog;
import research.ResearchPaper;
import research.ResearchProject;
import users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton storage class. It is the single in-memory source of system data.
 */
public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_FILE = "university_data.ser";

    private static DataStore instance;

    private final List<User> users;
    private final List<Course> courses;
    private final List<ResearchPaper> researchPapers;
    private final List<ResearchProject> researchProjects;
    private final List<RegistrationRequest> registrationRequests;
    private final List<News> news;
    private final List<UserActionLog> logs;

    private DataStore() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        researchPapers = new ArrayList<>();
        researchProjects = new ArrayList<>();
        registrationRequests = new ArrayList<>();
        news = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public List<ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    public List<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public List<News> getNews() {
        return news;
    }

    public List<UserActionLog> getLogs() {
        return logs;
    }

    public void save() throws IOException {
        save(DEFAULT_FILE);
    }

    public void save(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    public static DataStore load() throws IOException, ClassNotFoundException {
        return load(DEFAULT_FILE);
    }

    public static DataStore load(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            instance = (DataStore) in.readObject();
            return instance;
        }
    }

    public void clear() {
        users.clear();
        courses.clear();
        researchPapers.clear();
        researchProjects.clear();
        registrationRequests.clear();
        news.clear();
        logs.clear();
    }
}
