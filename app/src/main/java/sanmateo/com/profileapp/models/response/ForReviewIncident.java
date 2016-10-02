package sanmateo.com.profileapp.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 9/4/16.
 */
public class ForReviewIncident implements Parcelable {

    @SerializedName("uploader_id") int uploaderId;
    @SerializedName("uploader_name") String uploaderName;
    @SerializedName("uploader_email") String uploaderEmail;
    @SerializedName("uploader_contact_no") String uploaderContact;
    @SerializedName("uploader_gender") String uploaderGender;
    @SerializedName("uploader_address") String uploaderAddress;
    @SerializedName("uploader_pic_url") String uploaderPicUrl;
    @SerializedName("reporter_id") int reportedId;
    @SerializedName("reporter_name") String reporterName;
    @SerializedName("reporter_email") String reporterEmail;
    @SerializedName("reporter_address") String reporterAddress;
    @SerializedName("reporter_contact_no") String reporterContactNo;
    @SerializedName("reporter_gender") String reporterGender;
    @SerializedName("reporter_pic_url") String reporterPicUrl;
    @SerializedName("incident_report_id") int incidentReportId;
    @SerializedName("incident_id") int incidentId;
    @SerializedName("report_status") String reportStatus;
    @SerializedName("created_at") String createdAt;
    @SerializedName("deleted_at") String deletedAt;
    @SerializedName("remarks") String remarks;
    @SerializedName("incident_type") String incidentType;
    @SerializedName("incident_description") String incidentDescription;
    @SerializedName("incident_images") String incidentImages;
    @SerializedName("incident_status") String incidentStatus;
    @SerializedName("incident_address") String incidentAddress;
    @SerializedName("incident_latitude") long incidentLatitude;
    @SerializedName("incident_longitude") long incidentLongitude;

    public int getUploaderId() {
        return uploaderId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public String getUploaderEmail() {
        return uploaderEmail;
    }

    public String getUploaderContact() {
        return uploaderContact;
    }

    public String getUploaderGender() {
        return uploaderGender;
    }

    public String getUploaderAddress() {
        return uploaderAddress;
    }

    public String getUploaderPicUrl() {
        return uploaderPicUrl;
    }

    public int getReportedId() {
        return reportedId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getReporterAddress() {
        return reporterAddress;
    }

    public String getReporterContactNo() {
        return reporterContactNo;
    }

    public String getReporterGender() {
        return reporterGender;
    }

    public String getReporterPicUrl() {
        return reporterPicUrl;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public String getIncidentImages() {
        return incidentImages;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public long getIncidentLatitude() {
        return incidentLatitude;
    }

    public long getIncidentLongitude() {
        return incidentLongitude;
    }

    public int getIncidentReportId() {
        return incidentReportId;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uploaderId);
        parcel.writeString(this.uploaderName);
        parcel.writeString(this.uploaderEmail);
        parcel.writeString(this.uploaderContact);
        parcel.writeString(this.uploaderGender);
        parcel.writeString(this.uploaderAddress);
        parcel.writeString(this.uploaderPicUrl);
        parcel.writeInt(this.reportedId);
        parcel.writeString(this.reporterName);
        parcel.writeString(this.reporterEmail);
        parcel.writeString(this.reporterAddress);
        parcel.writeString(this.reporterContactNo);
        parcel.writeString(this.reporterGender);
        parcel.writeString(this.reporterPicUrl);
        parcel.writeInt(this.incidentReportId);
        parcel.writeInt(this.incidentId);
        parcel.writeString(this.reportStatus);
        parcel.writeString(this.createdAt);
        parcel.writeString(this.deletedAt);
        parcel.writeString(this.remarks);
        parcel.writeString(this.incidentType);
        parcel.writeString(this.incidentDescription);
        parcel.writeString(this.incidentImages);
        parcel.writeString(this.incidentStatus);
        parcel.writeString(this.incidentAddress);
        parcel.writeLong(this.incidentLatitude);
        parcel.writeLong(this.incidentLongitude);
    }

    public ForReviewIncident(Parcel in) {
        this.uploaderId = in.readInt();
        this.uploaderName = in.readString();
        this.uploaderEmail = in.readString();
        this.uploaderContact = in.readString();
        this.uploaderGender = in.readString();
        this.uploaderAddress = in.readString();
        this.uploaderPicUrl = in.readString();
        this.reportedId = in.readInt();
        this.reporterName = in.readString();
        this.reporterEmail = in.readString();
        this.reporterAddress = in.readString();
        this.reporterContactNo = in.readString();
        this.reporterGender = in.readString();
        this.reporterPicUrl = in.readString();
        this.incidentReportId = in.readInt();
        this.incidentId = in.readInt();
        this.reportStatus = in.readString();
        this.createdAt = in.readString();
        this.deletedAt = in.readString();
        this.remarks = in.readString();
        this.incidentType = in.readString();
        this.incidentDescription = in.readString();
        this.incidentImages = in.readString();
        this.incidentStatus = in.readString();
        this.incidentAddress = in.readString();
        this.incidentLatitude = in.readLong();
        this.incidentLongitude = in.readLong();
    }

    public static final Creator CREATOR = new Creator() {
        public ForReviewIncident createFromParcel(Parcel in) {
            return new ForReviewIncident(in);
        }

        public ForReviewIncident[] newArray(int size) {
            return new ForReviewIncident[size];
        }
    };
}
