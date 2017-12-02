package sanmateo.com.profileapp.news.local.saver;

import java.util.List;

import io.reactivex.Completable;
import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public interface RoomNewsSaver {


    Completable saveNews(News news);

    Completable saveNews(List<News> news);
}
