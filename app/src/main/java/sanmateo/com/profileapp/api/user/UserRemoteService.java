package sanmateo.com.profileapp.api.user;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public interface UserRemoteService {

    @POST("/api/v1/login")
    @FormUrlEncoded
    Single<UserDto> authenticateUser(@Field("email") String email,
                                     @Field("password") String password);
}
