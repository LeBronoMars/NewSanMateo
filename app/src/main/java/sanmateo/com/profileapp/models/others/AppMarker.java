package sanmateo.com.profileapp.models.others;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AppMarker {

    double latitude;
    double longitude;
    String title;
    String snippet;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;

    public AppMarker(LatLng latLng, String title, String snippet, String imageUrl1, String imageUrl2,
                     String imageUrl3) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.title = title;
        this.snippet = snippet;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
    }

    public AppMarker(double latitude, double longitude, String title, String snippet, String imageUrl1,
                     String imageUrl2, String imageUrl3) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.snippet = snippet;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public void setLatlng(LatLng latlng) {
        this.latitude = latlng.latitude;
        this.longitude = latlng.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl) {
        this.imageUrl1 = imageUrl;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }
}
