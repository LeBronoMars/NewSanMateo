package sanmateo.com.profileapp.news.usecase;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by rsbulanon on 26/11/2017.
 */

public interface NewsLoader {

    Single<List<News>> loadNews(int start, int limit);
}
