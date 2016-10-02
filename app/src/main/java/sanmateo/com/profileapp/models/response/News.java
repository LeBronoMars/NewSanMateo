package sanmateo.com.profileapp.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class News implements Parcelable {

    private int id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("deleted_at") private String deletedAt;
    private String title;
    private String body;
    @SerializedName("source_url") private String sourceUrl;
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("reported_by") private String reportedBy;
    private String status;

    public News(int id, String createdAt, String updatedAt, String deletedAt, String title,
                String body, String sourceUrl, String imageUrl, String reportedBy, String status) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.title = title;
        this.body = body;
        this.sourceUrl = sourceUrl;
        this.imageUrl = imageUrl;
        this.reportedBy = reportedBy;
        this.status = status;
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

    public String getBody() {
        return body;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.createdAt);
        parcel.writeString(this.updatedAt);
        parcel.writeString(this.deletedAt);
        parcel.writeString(this.title);
        parcel.writeString(this.body);
        parcel.writeString(this.sourceUrl);
        parcel.writeString(this.imageUrl);
        parcel.writeString(this.reportedBy);
        parcel.writeString(this.status);
    }

    public News(Parcel parcel) {
        this.id = parcel.readInt();
        this.createdAt = parcel.readString();
        this.updatedAt = parcel.readString();
        this.deletedAt = parcel.readString();
        this.title = parcel.readString();
        this.body = parcel.readString();
        this.sourceUrl = parcel.readString();
        this.imageUrl = parcel.readString();
        this.reportedBy = parcel.readString();
        this.status = parcel.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
