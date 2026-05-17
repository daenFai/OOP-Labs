package services;

import communication.News;
import storage.DataStore;
import users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified Observer pattern: users subscribe to news and receive notifications in console.
 */
public class NewsService {
    private final DataStore dataStore;
    private final List<User> subscribers;

    public NewsService(DataStore dataStore) {
        this.dataStore = dataStore;
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(User user) {
        if (!subscribers.contains(user)) {
            subscribers.add(user);
        }
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public News publishNews(String title, String content) {
        News news = new News(title, content);
        dataStore.getNews().add(news);
        notifySubscribers(news);
        return news;
    }

    private void notifySubscribers(News news) {
        for (User subscriber : subscribers) {
            System.out.println("Notification for " + subscriber.getUsername() + ": " + news.getTitle());
        }
    }
}
