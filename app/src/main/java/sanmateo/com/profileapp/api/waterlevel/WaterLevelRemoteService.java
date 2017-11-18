package sanmateo.com.profileapp.api.waterlevel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface WaterLevelRemoteService {

    @GET("/api/v1/water_level")
    Single<Response<List<WaterLevelDto>>> waterLevels(@Query("area") String area);
}
