package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Sort;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.NotificationsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.interfaces.ApiInterface;
import sanmateo.com.profileapp.models.response.Announcement;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.models.response.Notification;
import sanmateo.com.profileapp.models.response.WaterLevel;
import sanmateo.com.profileapp.models.response.Weather;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.RetrofitSingleton;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Integer.MAX_VALUE;
import static sanmateo.com.profileapp.models.response.Incident.FIRE;
import static sanmateo.com.profileapp.models.response.Incident.FLOODING;
import static sanmateo.com.profileapp.models.response.Incident.MISCELLANEOUS;
import static sanmateo.com.profileapp.models.response.Incident.SOLID_WASTE;
import static sanmateo.com.profileapp.models.response.Incident.TRAFFIC_ROAD;

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

    @BindView(R.id.tv_empty_notif)
    TextView tvEmptyNotif;

    private ApiInterface apiInterface;

    private Unbinder unbinder;

    private String token;

    private ArrayList<Notification> notifications = new ArrayList<>();

    private static final String ACTIVE = "active";

    private RealmHelper<Notification> notificationRealmHelper = new RealmHelper<>(Notification.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        unbinder = ButterKnife.bind(this);

        apiInterface = RetrofitSingleton.getInstance().getApiInterface();

        token = CurrentUserSingleton.getInstance().getCurrentUser().getToken();

        NotificationsAdapter adapter = new NotificationsAdapter(this, notifications);
        adapter.setOnSelectNotificationListener(selectedIndex -> {

            Notification notification = notifications.get(selectedIndex);
            notificationRealmHelper.openRealm();
            notification.setNotificationStatus("SEEN");
            notificationRealmHelper.replaceInto(notification);

            rvNotifications.getAdapter().notifyItemChanged(selectedIndex);

            if (notification.getNotificationType().equals("WATER_LEVEL")) {
                moveToOtherActivity(AlertLevelActivity.class);
            } else if (notification.getNotificationType().equals("WEATHER")) {
                Intent intent = new Intent(NotificationsActivity.this, WeatherForecastActivity.class);
                intent.putExtra("selectedIndex", 0);
                startActivity(intent);
                animateToLeft(NotificationsActivity.this);
            } else if (notification.getNotificationType().equals("STORM")) {
                Intent intent = new Intent(NotificationsActivity.this, WeatherForecastActivity.class);
                intent.putExtra("selectedIndex", 1);
                startActivity(intent);
                animateToLeft(NotificationsActivity.this);
            } else if (notification.getNotificationType().equals("INCIDENT")) {
                Intent intent = new Intent(NotificationsActivity.this, IncidentDetailActivity.class);
                intent.putExtra("selectedId", notification.getId());
                startActivity(intent);
                animateToLeft(NotificationsActivity.this);
            } else if (notification.getNotificationType().equals("ANNOUNCEMENT")) {
                moveToOtherActivity(PublicAnnouncementsActivity.class);
            }
        });

        rvNotifications.setAdapter(adapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));

        //getNotifications();
        getNotificationsFromDb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
        animateToRight(this);
    }

    @OnClick(R.id.iv_clear_notifs)
    public void clearNotifs() {
        runOnUiThread(() -> {
            notificationRealmHelper.deleteRecords();

            notifications.clear();
            rvNotifications.getAdapter().notifyDataSetChanged();

            tvEmptyNotif.setVisibility(notifications.isEmpty() ? VISIBLE : GONE);
            rvNotifications.setVisibility(notifications.isEmpty() ? GONE : VISIBLE);
        });
    }

    @Subscribe
    public void refreshNotificationsCount(HashMap<String, Object> map) {
        if (map.get("action").toString().equals("refreshNotificationCount")) {
            getNotificationsFromDb();
        }
    }

    private void getNotificationsFromDb() {
        runOnUiThread(() -> {
            notifications.clear();
            notifications.addAll(notificationRealmHelper.findAllSorted("date", Sort.DESCENDING));
            rvNotifications.getAdapter().notifyDataSetChanged();

            tvEmptyNotif.setVisibility(notifications.isEmpty() ? VISIBLE : GONE);
            rvNotifications.setVisibility(notifications.isEmpty() ? GONE : VISIBLE);
        });
    }

    private void getNotifications() {
        getIncidets()
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

    private Observable<Notification> getIncidets() {
        return apiInterface.getIncidents(token, 0, FIRE, ACTIVE)
                           .mergeWith(apiInterface.getIncidents(token, 0, FLOODING, ACTIVE))
                           .mergeWith(apiInterface.getIncidents(token, 0, SOLID_WASTE, ACTIVE))
                           .mergeWith(apiInterface.getIncidents(token, 0, TRAFFIC_ROAD, ACTIVE))
                           .mergeWith(apiInterface.getIncidents(token, 0, MISCELLANEOUS, ACTIVE))
                           .flatMap(Observable::fromIterable)
                           .flatMapSingle(this::mapIncidentToNotification);
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

            notification.setNotificationType("ANNOUNCEMENT");
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

            notification.setNotificationType("WATER_LEVEL");
            notification.setId(waterLevel.getId());
            notification.setTitle("Water Level Alert");
            notification.setDescription(waterLevel.getArea() + ": " + waterLevel.getWaterLevel() + " ft.");
            notification.setDate(waterLevel.getCreatedAt());
            notification.setWaterAlert(waterLevel.getAlert());

            return Single.just(notification);
        });
    }

    private Single<Notification> mapWeatherToNotification(Weather weather) {
        return Single.defer(() -> {
            Notification notification = new Notification();

            notification.setNotificationType("WEATHER");
            notification.setId(weather.id);
            notification.setTitle("Weather Update");
            notification.setDescription(weather.summary);
            notification.setDate(weather.createdAt);

            return Single.just(notification);
        });
    }

    private Single<Notification> mapIncidentToNotification(Incident incident) {
        return Single.defer(() -> {
            Notification notification = new Notification();

            notification.setNotificationType("INCIDENT");
            notification.setId(incident.getIncidentId());
            notification.setIncidentType(incident.getIncidentType());
            notification.setTitle("Incident : " + incident.getIncidentType());
            notification.setDescription(incident.getIncidentDescription());
            notification.setDate(incident.getIncidentDateReported());

            return Single.just(notification);
        });
    }
}
