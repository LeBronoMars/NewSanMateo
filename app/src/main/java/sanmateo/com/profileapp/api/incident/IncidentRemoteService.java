package sanmateo.com.profileapp.api.incident;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentRemoteService {


    @GET("/api/v1/incidents")
    Observable<IncidentDto> loadIncidents(@Query("start") int start,
                                          @Query("limit") int limit);
}
