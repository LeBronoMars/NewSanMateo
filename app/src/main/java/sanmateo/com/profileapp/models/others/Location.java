package sanmateo.com.profileapp.models.others;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ctmanalo on 7/19/16.
 */
public class Location {

    private String locationName;
    private String locationAddress;
    private String contactNo;
    private String imageUrl;
    private LatLng latLng;
    private String category;

    public Location(String locationName, String locationAddress, String contactNo, String imageUrl,
                    LatLng latLng, String category) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.contactNo = contactNo;
        this.imageUrl = imageUrl;
        this.latLng = latLng;
        this.category = category;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
