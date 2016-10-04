package sanmateo.com.profileapp.singletons;

import java.util.ArrayList;

import sanmateo.com.profileapp.models.response.News;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class NewsSingleton {

    private ArrayList<News> newsToday = new ArrayList<>();
    private ArrayList<News> newsPrevious = new ArrayList<>();
    private ArrayList<News> allNews = new ArrayList<>();

    private static NewsSingleton NEWS = new NewsSingleton();

    private NewsSingleton() {}

    public static NewsSingleton getInstance() {
        return NEWS;
    }

    public ArrayList<News> getNewsToday() {
        return newsToday;
    }

    public ArrayList<News> getAllNews() { return allNews; }

    public ArrayList<News> getNewsPrevious() {
        return newsPrevious;
    }

    public boolean isNewsExisting(final int id) {
        for (News n : getNewsToday()) {
            if (n.getId() == id) {
                return true;
            }
        }
        for (News n : getNewsPrevious()) {
            if (n.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void clearAll() {
        newsToday.clear();
        newsPrevious.clear();
        allNews.clear();
    }
}
