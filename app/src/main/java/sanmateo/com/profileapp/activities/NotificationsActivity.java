package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.NotificationsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.interfaces.ApiInterface;
import sanmateo.com.profileapp.models.response.Announcement;
import sanmateo.com.profileapp.models.response.Notification;
import sanmateo.com.profileapp.models.response.WaterLevel;
import sanmateo.com.profileapp.models.response.Weather;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.RetrofitSingleton;


import static java.lang.Integer.MAX_VALUE;
import static sanmateo.com.profileapp.models.response.NotificationType.ANNOUNCEMENT;
import static sanmateo.com.profileapp.models.response.NotificationType.WATER_LEVEL;
import static sanmateo.com.profileapp.models.response.NotificationType.WEATHER;

/**
 * Created by rsbulanon on 05/03/2018.
 */

public class NotificationsActivity extends BaseActivity {

    @BindString(R.string.water_level_batasan)
    String waterLevelBatasan;

    @BindString(R.string.water_level_montalban)
    String waterLevelMontalban;

    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;

    private ApiInterface apiInterface;

    private Unbinder unbinder;

    private String token;

    private ArrayList<Notification> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        unbinder = ButterKnife.bind(this);

        apiInterface = RetrofitSingleton.getInstance().getApiInterface();

        token = CurrentUserSingleton.getInstance().getCurrentUser().getToken();

        rvNotifications.setAdapter(new NotificationsAdapter(notifications));
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));

        getNotifications();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getNotifications() {
        getAnnouncements()
            .mergeWith(getWaterLevelByArea(waterLevelMontalban))
            .mergeWith(getWaterLevelByArea(waterLevelBatasan))
            .mergeWith(getWeather())
            .toSortedList((n1, n2) -> n1.getDate().compareTo(n2.getDate()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<Notification>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(List<Notification> notifs) {
                    notifications.clear();
                    notifications.addAll(notifs);
                    rvNotifications.getAdapter().notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("app", "error on loading notifications ---> " + e.getMessage());
                }
            });
    }

    private Observable<Notification> getAnnouncements() {
        return apiInterface.getAnnouncements(token, 0, MAX_VALUE)
                           .flatMap(Observable::fromIterable)
                           .flatMapSingle(this::mapAnnouncementToNotification);
    }

    private Observable<Notification> getWaterLevelByArea(String area) {
        return apiInterface.getWaterLevelByArea(token, area, MAX_VALUE)
                           .flatMap(Observable::fromIterable)
                           .flatMapSingle(this::mapWaterLevelToNotification);
    }

    private Observable<Notification> getWeather() {
        return apiInterface.getLatestWeather(token, MAX_VALUE)
                           .flatMap(Observable::fromIterable)
                           .flatMapSingle(this::mapWeatherToNotification);
    }

    private Single<Notification> mapAnnouncementToNotification(Announcement announcement) {
        return Single.defer(() -> {
            Notification notification = new Notification();

            notification.setNotificationType(ANNOUNCEMENT);
            notification.setId(announcement.getId());
            notification.setTitle(announcement.getTitle());
            notification.setDescription(announcement.getMessage());
            notification.setDate(announcement.getCreatedAt());

            return Single.just(notification);
        });
    }

    private Single<Notification> mapWaterLevelToNotification(WaterLevel waterLevel) {
        return Single.defer(() -> {
            Notification notification = new Notification();

            notification.setNotificationType(WATER_LEVEL);
            notification.setId(waterLevel.getId());
            notification.setTitle("Water Level Alert");
            notification.setDescription(waterLevel.getArea() + ": " + waterLevel.getWaterLevel() + " ft.");
            notification.setDate(waterLevel.getCreatedAt());

            return Single.just(notification);
        });
    }

    private Single<Notification> mapWeatherToNotification(Weather weather) {
        return Single.defer(() -> {
            Notification notification = new Notification();

            notification.setNotificationType(WEATHER);
            notification.setId(weather.id);
            notification.setTitle("Weather Update");
            notification.setDescription(weather.summary);
            notification.setDate(weather.createdAt);

            return Single.just(notification);
        });
    }
}
