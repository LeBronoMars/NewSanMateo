package sanmateo.com.profileapp.helpers;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.interfaces.ApiInterface;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.Announcement;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.models.response.Gallery;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.models.response.News;
import sanmateo.com.profileapp.models.response.Official;
import sanmateo.com.profileapp.models.response.WaterLevel;
import sanmateo.com.profileapp.models.response.Weather;
import sanmateo.com.profileapp.singletons.RetrofitSingleton;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class ApiRequestHelper {
    private ApiInterface apiInterface;
    private OnApiRequestListener onApiRequestListener;

    public ApiRequestHelper(OnApiRequestListener onApiRequestListener) {
        this.apiInterface = RetrofitSingleton.getInstance().getApiInterface();
        this.onApiRequestListener = onApiRequestListener;
    }

    public void authenticateUser(final String email, final String password) {
        onApiRequestListener.onApiRequestBegin(ApiAction.POST_AUTH);
        final Observable<AuthResponse> observable = apiInterface.authenticateUser(email, password);
        handleObservableResult(ApiAction.POST_AUTH, observable);
    }

    public void forgotPassword(final String email) {
        onApiRequestListener.onApiRequestBegin(ApiAction.POST_FORGOT_PASSWORD);
        final Observable<ResponseBody> observable = apiInterface.forgotPassword(email);
        handleObservableResult(ApiAction.POST_FORGOT_PASSWORD, observable);
    }

    public void createUser(final String firstName, final String lastName, final String contactNo,
                           final String gender, final String email, final String address,
                           final String userLevel, final String password) {
        onApiRequestListener.onApiRequestBegin(ApiAction.POST_REGISTER);
        final Observable<AuthResponse> observable = apiInterface.createUser(firstName, lastName,
                contactNo, gender, email, address, userLevel, password);
        handleObservableResult(ApiAction.POST_REGISTER, observable);
    }

    public void changePassword(final String token, final String email, final String oldPassword,
                               final String newPassword) {
        onApiRequestListener.onApiRequestBegin(ApiAction.PUT_CHANGE_PW);
        final Observable<GenericMessage> observable = apiInterface.changePassword(token, email,
                oldPassword, newPassword);
        handleObservableResult(ApiAction.PUT_CHANGE_PW, observable);
    }

    public void getNews(final String token, final int start, final int limit, final String status,
                        final String when) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_NEWS);
        final Observable<List<News>> observable = apiInterface.getNews(token, start, limit, status, when);
        handleObservableResult(ApiAction.GET_NEWS, observable);
    }

    public void getNewsById(final String token, final int id) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_NEWS_BY_ID);
        final Observable<News> observable = apiInterface.getNewsById(token, id);
        handleObservableResult(ApiAction.GET_NEWS_BY_ID, observable);
    }

    public void getAllIncidents(final String token, final int start, final String incidentType,
                                final String status) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_INCIDENTS);
        final Observable<List<Incident>> observable = apiInterface.getIncidents(token, start,
                incidentType, status);
        handleObservableResult(ApiAction.GET_INCIDENTS, observable);
    }

    public void getLatestIncidents(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_LATEST_INCIDENTS);
        final Observable<List<Incident>> observable = apiInterface.getLatestIncidents(token,
                incidentId);
        handleObservableResult(ApiAction.GET_LATEST_INCIDENTS, observable);
    }

    public void fileIncidentReport(final String token, final String address, final String description,
                                   final String incidentType, final double latitude, final double longitude,
                                   final int reportedBy, final String images) {
        onApiRequestListener.onApiRequestBegin(ApiAction.POST_INCIDENT_REPORT);
        final Observable<Incident> observable = apiInterface.fileNewIncidentReport(token,address,
                description,incidentType,latitude,longitude,reportedBy,images);
        handleObservableResult(ApiAction.POST_INCIDENT_REPORT, observable);
    }

    public void reportMaliciousIncidentReport(final String token, final int incidentId,
                                              final int postedBy, final int reportedBy,
                                              final String remarks) {
        onApiRequestListener.onApiRequestBegin(ApiAction.POST_MALICIOUS_REPORT);
        final Observable<ResponseBody> observable = apiInterface.reportMaliciousIncidentReport(token,
                incidentId, postedBy, reportedBy, remarks);
        handleObservableResult(ApiAction.POST_MALICIOUS_REPORT, observable);
    }

    public void changeProfilePic(final String token, final int userId, final String newPicUrl) {
        onApiRequestListener.onApiRequestBegin(ApiAction.PUT_CHANGE_PROFILE_PIC);
        final Observable<GenericMessage> observable = apiInterface.changeProfilePic(token, userId,
                newPicUrl);
        handleObservableResult(ApiAction.PUT_CHANGE_PROFILE_PIC, observable);
    }

    public void getOfficials(final String token) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_OFFICIALS);
        final Observable<List<Official>> observable = apiInterface.getOfficials(token);
        handleObservableResult(ApiAction.GET_OFFICIALS, observable);
    }

    public void getGalleries(final String token) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_PHOTOS);
        final Observable<ArrayList<Gallery>> observable = apiInterface.getGalleries(token);
        handleObservableResult(ApiAction.GET_PHOTOS, observable);
    }

    public void getAnnouncements(final String token, final int start, final int limit) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_ANNOUNCEMENTS);
        final Observable<List<Announcement>> observable = apiInterface.getAnnouncements(token,start,limit);
        handleObservableResult(ApiAction.GET_ANNOUNCEMENTS, observable);
    }

    public void getLatestAnnouncements(final String token, final int announcementId) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_LATEST_ANNOUNCEMENTS);
        final Observable<List<Announcement>> observable = apiInterface.getLatestAnnouncements(token,announcementId);
        handleObservableResult(ApiAction.GET_LATEST_ANNOUNCEMENTS, observable);
    }

    public void getWaterLevels(final String token, final int start, final int limit) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_WATER_LEVEL);
        final Observable<List<WaterLevel>> observable = apiInterface.getWaterLevels(token,start,limit);
        handleObservableResult(ApiAction.GET_WATER_LEVEL, observable);
    }

    public void getLatestWaterLevels(final String token, final int id, final String area) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_LATEST_WATER_LEVEL);
        final Observable<List<WaterLevel>> observable = apiInterface.getLatestWaterLevels(token,id, area);
        handleObservableResult(ApiAction.GET_LATEST_WATER_LEVEL, observable);
    }

    public void getWaterLevelByArea(final String token, final String area) {
        onApiRequestListener.onApiRequestBegin(ApiAction.GET_WATER_LEVEL_BY_AREA);
        final Observable<List<WaterLevel>> observable = apiInterface.getWaterLevelByArea(token,
                area);
        handleObservableResult(ApiAction.GET_WATER_LEVEL_BY_AREA, observable);
    }

    public void getWeather(final String token) {
        final Observable<List<Weather>> observable = apiInterface.getLatestWeather(token, 1);
        handleObservableResult(ApiAction.GET_WEATHER, observable);
    }

    /**
     * handle api result using lambda
     *
     * @param action     identification of the current api request
     * @param observable actual process of the api request
     */
    private void handleObservableResult(final ApiAction action, final Observable observable) {
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(result -> onApiRequestListener.onApiRequestSuccess(action, result),
                        throwable -> onApiRequestListener.onApiRequestFailed(action,
                                (Throwable) throwable),
                        () -> LogHelper.log("api", "Api request completed --> " + action));
    }
}
