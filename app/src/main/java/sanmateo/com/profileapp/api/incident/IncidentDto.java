package sanmateo.com.profileapp.api.incident;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class IncidentDto {

    public String images;
    
    @SerializedName("incident_id")
    public int incidentId;

    @SerializedName("incident_date_reported")
    public String incidentDateReported;

    @SerializedName("incident_date_updated")
    public String incidentDateUpdated;

    @SerializedName("incident_address")
    public String incidentAddress;

    @SerializedName("incident_description")
    public String incidentDescription;

    @SerializedName("incident_status")
    public String incidentStatus;

    @SerializedName("incident_type")
    public String incidentType;

    public double latitude;

    public double longitude;

    public String remarks;

    @SerializedName("reporter_id")
    public int reporterId;

    @SerializedName("reporter_name")
    public String reporterName;

    @SerializedName("reporter_contact_no")
    public String reporterContactNo;

    @SerializedName("reporter_email")
    public String reporterEmail;

    @SerializedName("reporter_address")
    public String reportedAddress;

    @SerializedName("reporter_pic_url")
    public String reporterPicUrl;

    public String status;

    public IncidentDto() {
        this.images = "";
        this.incidentDateReported = "";
        this.incidentDateUpdated = "";
        this.incidentAddress = "";
        this.incidentDescription = "";
        this.incidentStatus = "";
        this.incidentType = "";
        this.remarks = "";
        this.reporterName = "";
        this.reporterContactNo = "";
        this.reporterEmail = "";
        this.reportedAddress = "";
        this.reporterPicUrl = "";
        this.status = "";
    }
}
