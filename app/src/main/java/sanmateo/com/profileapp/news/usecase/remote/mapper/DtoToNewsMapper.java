package sanmateo.com.profileapp.news.usecase.remote.mapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 28/11/2017.
 */

public class DtoToNewsMapper implements ObservableTransformer<NewsDto, News> {

    @Override
    public ObservableSource<News> apply(Observable<NewsDto> upstream) {
        return upstream.flatMap(newsDto -> {
            News news = new News();

            news.body = newsDto.body;
            news.createdAt = newsDto.createdAt;
            news.deletedAt = newsDto.deletedAt;
            news.id = newsDto.id;
            news.imageUrl = newsDto.imageUrl;
            news.reportedBy = newsDto.reportedBy;
            news.status = newsDto.status;
            news.sourceUrl = newsDto.sourceUrl;
            news.title = newsDto.title;
            news.updatedAt = newsDto.updatedAt;
            return Observable.just(news);
        });
    }
}
