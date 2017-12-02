package sanmateo.com.profileapp.news.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import sanmateo.com.profileapp.news.local.loader.RoomNewsLoader;
import sanmateo.com.profileapp.news.local.saver.RoomNewsSaver;
import sanmateo.com.profileapp.news.usecase.remote.NewsRemoteLoader;
import sanmateo.com.profileapp.news.usecase.remote.mapper.DtoToNewsMapper;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class DefaultNewsLoader implements NewsLoader {

    private NewsRemoteLoader newsRemoteLoader;

    private RoomNewsLoader roomNewsLoader;

    private RoomNewsSaver roomNewsSaver;

    @Inject
    public DefaultNewsLoader(NewsRemoteLoader newsRemoteLoader, RoomNewsLoader roomNewsLoader,
                             RoomNewsSaver roomNewsSaver) {
        this.newsRemoteLoader = newsRemoteLoader;
        this.roomNewsLoader = roomNewsLoader;
        this.roomNewsSaver = roomNewsSaver;
    }

    @Override
    public Single<List<News>> loadNews(int start, int limit) {
        return newsRemoteLoader.loadNews(start, limit)
                               .compose(new DtoToNewsMapper())
                               .flatMapSingle(this::saveToLocal)
                               .onErrorResumeNext(loadFromLocal())
                               .toList();
    }

    public Observable<News> loadFromLocal() {
        return roomNewsLoader.loadNews()
                             .flatMapObservable(Observable::fromIterable);
    }

    private Single<News> saveToLocal(News news) {
        return roomNewsSaver.saveNews(news)
                                  .andThen(Single.just(news));
    }
}
