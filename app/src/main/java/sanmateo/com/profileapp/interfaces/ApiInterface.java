package sanmateo.com.profileapp.interfaces;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import sanmateo.com.profileapp.models.response.AuthResponse;

/**
 * Created by rsbulanon on 10/2/16.
 */

public interface ApiInterface {

    /**
     * Authenticate user
     *
     * @param email email of user trying to login
     * @param password password of user trying to
     *
     * @return AuthResponse which includes the basic info of info of successfully
     *                      authenticated user together with Token
     * */
    @POST("/api/v1/login")
    @FormUrlEncoded
    Observable<AuthResponse> authenticateUser(@Field("email") String email,
                                              @Field("password") String password);
}
