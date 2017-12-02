package sanmateo.com.profileapp.news.local.loader;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public interface RoomNewsLoader {

   Single<Long> count();

   Maybe<List<News>> loadNews();
}
