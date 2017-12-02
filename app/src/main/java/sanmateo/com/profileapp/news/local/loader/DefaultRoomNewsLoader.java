package sanmateo.com.profileapp.news.local.loader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.news.local.NewsDao;
import sanmateo.com.profileapp.news.usecase.News;
import sanmateo.com.profileapp.user.local.NoQueryResultException;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class DefaultRoomNewsLoader implements RoomNewsLoader {

    private NewsDao newsDao;

    @Inject
    public DefaultRoomNewsLoader(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public Single<Long> count() {
        return newsDao.count();
    }

    @Override
    public Maybe<List<News>> loadNews() {
        return newsDao.findAll()
                      .switchIfEmpty(Maybe.error(NoQueryResultException::new));
    }
}
