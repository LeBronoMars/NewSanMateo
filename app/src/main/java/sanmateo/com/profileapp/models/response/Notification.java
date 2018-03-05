package sanmateo.com.profileapp.models.response;

/**
 * Created by rsbulanon on 05/03/2018.
 */

public class Notification {

    private NotificationType notificationType;

    private String date;

    private String title;

    private String description;

    private int id;

    public Notification(NotificationType notificationType,
                        String date,
                        String title,
                        String description,
                        int id) {
        this.notificationType = notificationType;
        this.date = date;
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public Notification() {
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
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
}
