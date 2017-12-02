package sanmateo.com.profileapp.api.news;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rsbulanon on 28/11/2017.
 */

public interface NewsRemoteService {

    @GET("/api/v1/news")
    Observable<NewsDto> loadNews(@Query("start") int start,
                                 @Query("limit") int limit);
}
