package sanmateo.com.profileapp.news.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import sanmateo.com.profileapp.news.usecase.remote.NewsRemoteLoader;
import sanmateo.com.profileapp.news.usecase.remote.mapper.DtoToNewsMapper;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class DefaultNewsLoader implements NewsLoader {

    private NewsRemoteLoader newsRemoteLoader;

    @Inject
    public DefaultNewsLoader(NewsRemoteLoader newsRemoteLoader) {
        this.newsRemoteLoader = newsRemoteLoader;
    }

    @Override
    public Single<List<News>> loadNews(int start, int limit) {
        return newsRemoteLoader.loadNews(start, limit)
                               .compose(new DtoToNewsMapper())
                               .toList();
    }
}
