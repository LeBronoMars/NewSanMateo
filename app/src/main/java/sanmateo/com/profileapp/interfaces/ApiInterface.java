package sanmateo.com.profileapp.interfaces;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import sanmateo.com.profileapp.models.response.Announcement;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.models.response.Gallery;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.models.response.News;
import sanmateo.com.profileapp.models.response.Official;
import sanmateo.com.profileapp.models.response.WaterLevel;
import sanmateo.com.profileapp.models.response.Weather;

/**
 * Created by rsbulanon on 10/2/16.
 */

public interface ApiInterface {

    /**
     * Authenticate user
     *
     * @param email email of user trying to login
     * @param password password of user trying to
     * @return AuthResponse which includes the basic info of info of successfully
     * authenticated user together with Token
     */
    @POST("/api/v1/login")
    @FormUrlEncoded
    Observable<AuthResponse> authenticateUser(@Field("email") String email,
                                              @Field("password") String password);

    /**
     * Change password
     *
     * @param email email where the new password will be sent
     */
    @POST("/api/v1/forgot_password")
    @FormUrlEncoded
    Observable<ResponseBody> forgotPassword(@Field("email") String email);

    /**
     * create user
     */
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
     */
    @GET("/api/v1/news")
    Observable<List<News>> getNews(@Header("Authorization") String token,
                                   @Query("start") int start,
                                   @Query("limit") int limit,
                                   @Query("status") String status,
                                   @Query("when") String when);

    /**
     * get news by id
     *
     * @param token represents the user that trying to make the request
     * @param id unique identification of news to fetch
     */
    @GET("/api/v1/news/{id}")
    Observable<News> getNewsById(@Header("Authorization") String token,
                                 @Path("id") int id);

    /**
     * Get all incidents
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param incidentType filter incidents by incident type
     * @param status filter incidents by status
     * @return List of Incidents that matches all the given parameters above
     */
    @GET("/api/v1/incidents")
    Observable<List<Incident>> getIncidents(@Header("Authorization") String token,
                                            @Query("start") int start,
                                            @Query("incident_type") String incidentType,
                                            @Query("status") String status);

    /**
     * Get all latest incidents
     *
     * @param token represents the user that trying to make the request
     * @param incidentId unique identification of incident report
     * @return List of Incident whose id is greater than or equal to provided incident_id
     */
    @GET("/api/v1/incidents/latest/{incident_id}")
    Observable<List<Incident>> getLatestIncidents(@Header("Authorization") String token,
                                                  @Path("incident_id") int incidentId);

    /**
     * file new incident report
     *
     * @param token represents the user that trying to make the request
     * @param address location of the incident
     * @param description a brief description or details of the incident
     * @param incidentType classification of incident
     * @param latitude x coordinate of incident
     * @param longitude y coordinate of incident
     * @param reportedBy id of the user who reported the incident
     * @param images url of uploaded images of the incident
     * @return Incident model which contains the full detail of the newly created incident report
     */
    @POST("/api/v1/incident")
    @FormUrlEncoded
    Observable<Incident> fileNewIncidentReport(@Header("Authorization") String token,
                                               @Field("address") String address,
                                               @Field("description") String description,
                                               @Field("incident_type") String incidentType,
                                               @Field("latitude") double latitude,
                                               @Field("longitude") double longitude,
                                               @Field("reported_by") int reportedBy,
                                               @Field("images") String images);

    /**
     * report malicious or improper incident report
     *
     * @param token represents the user that trying to make the request
     * @param incidentId unique identification of posted incident report
     * @param postedBy user who posted the incident report
     * @param reportedBy user who is reported the possible malicious incident report
     * @param remarks brief description or explanation of the case
     */
    @POST("/api/v1/report")
    @FormUrlEncoded
    Observable<ResponseBody> reportMaliciousIncidentReport(@Header("Authorization") String token,
                                                           @Field("incident_id") int incidentId,
                                                           @Field("posted_by") int postedBy,
                                                           @Field("reported_by") int reportedBy,
                                                           @Field("remarks") String remarks);

    /**
     * change profile pic
     *
     * @param token represents the user that trying to make the request
     * @param userId id of the current user
     * @param newPicUrl url of the newly uploaded image to aws s3
     */
    @PUT("/api/v1/change_profile_pic")
    @FormUrlEncoded
    Observable<GenericMessage> changeProfilePic(@Header("Authorization") String token,
                                                @Field("user_id") int userId,
                                                @Field("new_pic_url") String newPicUrl);

    /**
     * change password
     *
     * @param token represents the user that trying to make the request
     * @param email email of user trying to change password
     * @param oldPassword current password of the user
     * @param newPassword new password of the user
     */
    @PUT("/api/v1/change_password")
    @FormUrlEncoded
    Observable<GenericMessage> changePassword(@Header("Authorization") String token,
                                              @Field("email") String email,
                                              @Field("old_password") String oldPassword,
                                              @Field("new_password") String newPassword);

    /**
     * get all list of active officials
     *
     * @param token represents the user that trying to make the request
     */
    @GET("/api/v1/officials")
    Observable<List<Official>> getOfficials(@Header("Authorization") String token);

    /**
     * get all gallery photos
     *
     * @param token represents the user that trying to make the request
     */
    @GET("/api/v1/galleries")
    Observable<ArrayList<Gallery>> getGalleries(@Header("Authorization") String token);

    /**
     * get all announcements
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param limit size of expected result
     */
    @GET("/api/v1/announcements")
    Observable<List<Announcement>> getAnnouncements(@Header("Authorization") String token,
                                                    @Query("start") int start,
                                                    @Query("limit") int limit);

    /**
     * get latest announcements
     *
     * @param token represents the user that trying to make the request
     * @param announcementId offset of query
     */
    @GET("/api/v1/announcements/latest/{id}")
    Observable<List<Announcement>> getLatestAnnouncements(@Header("Authorization") String token,
                                                          @Path("id") int announcementId);

    /**
     * get all water level by area
     *
     * @param token represents the user that trying to make the request
     * @param area defines the area of the requested water level
     */
    @GET("/api/v1/water_level/filter")
    Observable<List<WaterLevel>> getWaterLevelByArea(@Header("Authorization") String token,
                                                     @Query("area") String area);

    @GET("/api/v1/water_level/filter")
    Observable<List<WaterLevel>> getWaterLevelByArea(@Header("Authorization") String token,
                                                     @Query("area") String area,
                                                     @Query("limit") int limit);

    /**
     * get all water level notification
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param limit size of expected result
     */
    @GET("/api/v1/water_level")
    Observable<List<WaterLevel>> getWaterLevels(@Header("Authorization") String token,
                                                @Query("start") int start,
                                                @Query("limit") int limit);

    /**
     * get latest water level notifications
     *
     * @param token represents the user that trying to make the request
     * @param id latest record id
     * @param area name of flood prone area in san mateo
     */
    @GET("/api/v1/water_level/latest/{id}")
    Observable<List<WaterLevel>> getLatestWaterLevels(@Header("Authorization") String token,
                                                      @Path("id") int id,
                                                      @Query("area") String area);

    @GET("/api/v1/weather/reading")
    Observable<List<Weather>> getLatestWeather(@Header("Authorization") String token,
                                               @Query("limit") int limit);

}
