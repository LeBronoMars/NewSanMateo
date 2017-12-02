package sanmateo.com.profileapp.news.usecase.remote;

import io.reactivex.Observable;
import sanmateo.com.profileapp.api.news.NewsDto;

/**
 * Created by rsbulanon on 28/11/2017.
 */

public interface NewsRemoteLoader {

    Observable<NewsDto> loadNews(int start, int limit);
}
