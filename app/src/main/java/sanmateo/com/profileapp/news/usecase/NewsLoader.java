package sanmateo.com.profileapp.news.usecase;

import io.reactivex.Observable;
import sanmateo.com.profileapp.models.response.News;

/**
 * Created by rsbulanon on 26/11/2017.
 */

public interface NewsLoader {

    Observable<News> loadNews();
}
