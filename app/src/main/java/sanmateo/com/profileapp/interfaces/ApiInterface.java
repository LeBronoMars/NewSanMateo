package sanmateo.com.profileapp.interfaces;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.models.response.News;

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

    /**
     * create user
     * */
    @POST("/api/v1/user")
    @FormUrlEncoded
    Observable<AuthResponse> createUser(@Field("first_name") String firstName,
                                        @Field("last_name") String lastName,
                                        @Field("contact_no") String contactNo,
                                        @Field("gender") String gender,
                                        @Field("email") String email,
                                        @Field("address") String address,
                                        @Field("user_level") String userLevel,
                                        @Field("password") String password);

    /**
     * get all news
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param limit size of expected result
     * @param status filter news by their status
     * @param when to segregate news for today and previous
     *
     * */
    @GET("/api/v1/news")
    Observable<List<News>> getNews(@Header("Authorization") String token,
                                   @Query("start") int start,
                                   @Query("limit") int limit,
                                   @Query("status") String status,
                                   @Query("when") String when);

    /**
     * change password
     *
     * @param token represents the user that trying to make the request
     * @param email email of user trying to change password
     * @param oldPassword current password of the user
     * @param newPassword new password of the user
     * */
    @PUT("/api/v1/change_password")
    @FormUrlEncoded
    Observable<GenericMessage> changePassword(@Header("Authorization") String token,
                                              @Field("email") String email,
                                              @Field("old_password") String oldPassword,
                                              @Field("new_password") String newPassword);
}
