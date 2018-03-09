package sanmateo.com.profileapp.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sanmateo.com.profileapp.BuildConfig;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.AlertLevelActivity;
import sanmateo.com.profileapp.activities.NewHomeActivity;
import sanmateo.com.profileapp.activities.PublicAnnouncementsActivity;
import sanmateo.com.profileapp.activities.WeatherForecastActivity;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.NotificationHelper;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.models.response.Notification;
import sanmateo.com.profileapp.singletons.BusSingleton;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.IncidentsSingleton;


/**
 * Created by rsbulanon on 6/28/16.
 */
public class PusherService extends Service {
    private CurrentUserSingleton currentUserSingleton;
    private IncidentsSingleton incidentsSingleton;
    private RealmHelper<Notification> notificationRealmHelper = new RealmHelper<>(Notification.class);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentUserSingleton = CurrentUserSingleton.getInstance();
        incidentsSingleton = IncidentsSingleton.getInstance();

        /** start fore ground */
        final Intent notificationIntent = new Intent(this, NewHomeActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.san_mateo_logo)
                .setContentText(getString(R.string.app_name))
                .setContentIntent(pendingIntent).build();

       // startForeground(9999, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final PusherOptions options = new PusherOptions();
        options.setCluster(BuildConfig.PUSHER_CLUSTER);
        final Pusher pusher = new Pusher(BuildConfig.PUSHER_CLIENT_ID, options);

        /** listen to public channel */
        final Channel publicChannel = pusher.subscribe("clients");
        publicChannel.bind("san_mateo_event", (channelName, eventName, data) -> {
            /** manage, construct and display local push notification */
            try {
                final JSONObject json = new JSONObject(data);

                if (json.has("action")) {

                    Notification notification = new Notification();
                    notification.setId(json.getInt("id"));
                    notification.setDate(json.getString("created_at"));

                    final String action = json.getString("action");
                    final int id = Integer.valueOf(json.getInt("id"));

                    if (action.equals("new incident")) {
                        /** new incident notification */
                        PrefsHelper.setBoolean(PusherService.this, "refresh_incidents", true);
                        NotificationHelper.displayNotification(id, PusherService.this,
                                       "Incident : " + json.getString("incident_type"),
                                       json.getString("content"), null);

                        notification.setNotificationType("INCIDENT");
                        notification.setIncidentType(json.getString("incident_type"));
                        notification.setTitle("Incident : " + json.getString("incident_type"));
                        notification.setDescription(json.getString("content"));
                    } else if (action.equals("block report")) {
                        /** new incident notification */
                        final int reportedBy = Integer.valueOf(json.getString("reported_by"));
                        if (currentUserSingleton.getCurrentUser().getId() == reportedBy) {
                            LogHelper.log("pusher", "Show notification for blocked report");
                            NotificationHelper.displayNotification(id, PusherService.this,
                                    "Your report was blocked by the admin", json.getString("remarks"), null);
                        }
                        /** removed blocked incident from list of active incident reports
                         *  incidents singleton */
                        final int toRemoveIncidentId = Integer.valueOf(json.getString("id"));
                        for (int i = 0; i < incidentsSingleton.getIncidents("active").size(); i++) {
                            final Incident incident = incidentsSingleton.getIncidents("active").get(i);
                            if (incident.getIncidentId() == toRemoveIncidentId) {
                                incidentsSingleton.getIncidents("active").remove(i);
                            }
                        }
                    } else if (action.equals("news created")) {
                        NotificationHelper.displayNotification(id, PusherService.this,
                                json.getString("title"), json.getString("reported_by"), null);
                    } else if (action.equals("announcements")) {
                        PrefsHelper.setBoolean(PusherService.this, "has_notifications", true);
                        PrefsHelper.setBoolean(PusherService.this, "refresh_announcements", true);
                        NotificationHelper.displayNotification(id, PusherService.this,
                                json.getString("title"), json.getString("message"),
                                PublicAnnouncementsActivity.class);

                        notification.setNotificationType("ANNOUNCEMENT");
                        notification.setTitle(json.getString("title"));
                        notification.setDescription(json.getString("message"));
                    } else if (action.equals("water level")) {
                        PrefsHelper.setBoolean(PusherService.this, "has_notifications", true);
                        PrefsHelper.setBoolean(PusherService.this, "refresh_water_level", true);
                        NotificationHelper.displayNotification(id, PusherService.this,
                                json.getString("title"), json.getString("message"),
                                AlertLevelActivity.class, json.getString("area"));

                        notification.setNotificationType("WATER_LEVEL");
                        notification.setTitle("Water Level Alert");
                        notification.setDescription(json.getString("area") + ": " + json.getString("level") + " ft.");
                        notification.setWaterAlert(json.getString("message"));
                    } else if (action.equals("weather")) {
                        NotificationHelper.displayNotification(id, PusherService.this,
                                                               json.getString("title"),
                                                               json.getString("message"),
                                                               WeatherForecastActivity.class);

                        notification.setNotificationType("WEATHER");
                        notification.setTitle("Weather Update: " + json.getString("title"));
                        notification.setDescription(json.getString("message"));
                    } else if (action.equals("storm")) {
                        NotificationHelper.displayNotification(id, PusherService.this,
                                                               json.getString("title"),
                                                               json.getString("message"),
                                                               WeatherForecastActivity.class);

                        notification.setNotificationType("STORM");
                        notification.setTitle("Weather Update: " + json.getString("title"));
                        notification.setDescription(json.getString("message"));
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        notificationRealmHelper.replaceInto(notification);
                        LogHelper.log("pusher", "must save to local");
                    });
                }
            } catch (JSONException e) {
                LogHelper.log("pusher", "unable to manage,construct and display push notification --> " + e);
            }

            /** broadcast received push notification */
            final HashMap<String, Object> map = new HashMap<>();
            map.put("channel", channelName);
            map.put("data", data);
            map.put("action", "refreshNotificationCount");
            BusSingleton.getInstance().post(map);
        });

        /** listen to private channel using user's email */
        if (currentUserSingleton.getCurrentUser() != null && currentUserSingleton.getCurrentUser().getEmail() != null) {
            final Channel privateChannel = pusher.subscribe(currentUserSingleton.getCurrentUser().getEmail());
            privateChannel.bind("san_mateo_event", (channelName, eventName, data) -> {
                try {
                    final JSONObject json = new JSONObject(data);
                    if (json.has("action")) {
                        final String action = json.getString("action");
                        final int id = Integer.valueOf(json.getInt("id"));

                        if (action.equals("incident_approval")) {
                            NotificationHelper.displayNotification(id, PusherService.this,
                                    json.getString("title"), json.getString("content"), null);
                        }
                    } else {

                    }
                    /** broadcast received push notification */
                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("channel", channelName);
                    map.put("data", data);
                    BusSingleton.getInstance().post(map);
                } catch (JSONException e) {
                    LogHelper.log("pusher", "unable to listen to own channel using email --> " + e);
                }
            });

            pusher.connect(new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                    Log.d("pusher", "connection state changed ---> " + connectionStateChange.getCurrentState().name());
                }

                @Override
                public void onError(String s, String s1, Exception e) {
                    Log.d("pusher", "on error ---> " + s + " s1 --> " + s1);
                }
            });
        }
        return Service.START_STICKY;
    }
}
