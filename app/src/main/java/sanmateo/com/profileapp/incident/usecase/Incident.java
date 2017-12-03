package sanmateo.com.profileapp.incident.usecase;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class Incident {

    public String images;

    public int incidentId;

    public String incidentAddress;

    public String incidentDateReported;

    public String incidentDateUpdated;

    public String incidentDescription;

    public String incidentStatus;

    public String incidentType;

    public double latitude;

    public double longitude;

    public String remarks;

    public String reportedAddress;

    public String reporterContactNo;

    public String reporterEmail;

    public int reporterId;

    public String reporterName;

    public String reporterPicUrl;

    public String status;

    public Incident() {
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
