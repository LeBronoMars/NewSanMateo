package sanmateo.com.profileapp.news.local.saver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import sanmateo.com.profileapp.news.local.NewsDao;
import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class DefaultRoomNewsSaver implements RoomNewsSaver {

    private NewsDao newsDao;

    @Inject
    public DefaultRoomNewsSaver(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public Completable saveNews(News news) {
        return Completable.defer(() -> {
            newsDao.insert(news);
            return Completable.complete();
        });
    }

    @Override
    public Completable saveNews(List<News> news) {
        return Completable.defer(() -> {
            newsDao.insertAll(news);
            return Completable.complete();
        });
    }
}
