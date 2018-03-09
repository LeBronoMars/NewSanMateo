package sanmateo.com.profileapp.models.response;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rsbulanon on 05/03/2018.
 */

public class Notification extends RealmObject {

    private String notificationType;

    private String date;

    private String title;

    private String description;

    @PrimaryKey
    private int id;

    private String waterAlert;

    private String incidentType;

    private String notificationStatus;

    public Notification(String notificationType,
                        String date,
                        String title,
                        String description, int id, String waterAlert, String incidentType) {
        this.notificationType = notificationType;
        this.date = date;
        this.title = title;
        this.description = description;
        this.id = id;
        this.waterAlert = waterAlert;
        this.incidentType = incidentType;
        this.notificationStatus = "UNSEEN";
    }

    public Notification() {
        this.notificationStatus = "UNSEEN";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWaterAlert() {
        return waterAlert;
    }

    public void setWaterAlert(String waterAlert) {
        this.waterAlert = waterAlert;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
