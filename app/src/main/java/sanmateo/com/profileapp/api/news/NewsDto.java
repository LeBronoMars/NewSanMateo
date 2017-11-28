package sanmateo.com.profileapp.api.news;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 28/11/2017.
 */

public class NewsDto {

    public String body;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("deleted_at")
    public String deletedAt;

    public int id;

    @SerializedName("image_url")
    public String imageUrl;

    @SerializedName("reported_by")
    public String reportedBy;

    @SerializedName("source_url")
    public String sourceUrl;

    public String status;

    public String title;

    @SerializedName("updated_at")
    public String updatedAt;

    public NewsDto() {
        this.body = "";
        this.createdAt = "";
        this.deletedAt = "";
        this.imageUrl = "";
        this.reportedBy = "";
        this.sourceUrl = "";
        this.status = "";
        this.title = "";
        this.updatedAt = "";
    }
}
