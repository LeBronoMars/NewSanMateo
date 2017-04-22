package sanmateo.com.profileapp.models.response;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class Announcement extends RealmObject {

    @PrimaryKey private int id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    private String title;
    private String message;

    public Announcement() {
    }

    public Announcement(int id, String createdAt, String updatedAt, String deletedAt, String title, String message) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.title = title;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
