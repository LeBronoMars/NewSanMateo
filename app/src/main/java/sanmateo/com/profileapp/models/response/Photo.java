package sanmateo.com.profileapp.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ctmanalo on 8/15/16.
 */
public class Photo {

    @SerializedName("id") private int id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at")  private String updatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    private String title;
    @SerializedName("image_url") private String imageUrl;
    private String description;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}

