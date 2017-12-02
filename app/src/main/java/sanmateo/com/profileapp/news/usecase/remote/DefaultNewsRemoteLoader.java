package sanmateo.com.profileapp.news.usecase.remote;

import javax.inject.Inject;

import io.reactivex.Observable;
import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.api.news.NewsRemoteService;

/**
 * Created by rsbulanon on 28/11/2017.
 */

public class DefaultNewsRemoteLoader implements NewsRemoteLoader {

    private NewsRemoteService newsRemoteService;

    @Inject
    public DefaultNewsRemoteLoader(NewsRemoteService newsRemoteService) {
        this.newsRemoteService = newsRemoteService;
    }

    @Override
    public Observable<NewsDto> loadNews(int start, int limit) {
        return newsRemoteService.loadNews(start, limit);
    }
}
