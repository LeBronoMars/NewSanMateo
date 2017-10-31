package sanmateo.com.profileapp.models.others;

import com.google.android.gms.maps.model.LatLng;

import sanmateo.com.profileapp.R;


import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_AUTHENTIC_RESTAURANT;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_BARANGAY;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_HEALTH;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_HERITAGE_SITE;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_MUNICIPAL;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_RESORT;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_SHOPPING_MALL;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_SOCIAL_AND_DEVELOPMENT;

public class Location {

    private String locationName;
    private String locationAddress;
    private String contactNo;
    private String imageUrl;
    private LatLng latLng;
    private String category;
    private int marker;

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

    public int getMarker() {
        if (category.equals(CATEGORY_BARANGAY) || category.equals(CATEGORY_MUNICIPAL) ||
            category.equals(CATEGORY_SOCIAL_AND_DEVELOPMENT)) {
            return R.drawable.ic_map_gov_29dp;
        } else if (category.equals(CATEGORY_HEALTH)) {
            return R.drawable.ic_map_hospital_29dp;
        } else if (category.equals(CATEGORY_RESORT) || category.equals(CATEGORY_HERITAGE_SITE)) {
            return R.drawable.ic_map_tourist_29dp;
        } else if (category.equals(CATEGORY_SHOPPING_MALL)) {
            return R.drawable.ic_map_shop_29dp;
        } else if (category.equals(CATEGORY_AUTHENTIC_RESTAURANT)) {
            return R.drawable.ic_map_food_29dp;
        } else  {
            return R.drawable.ic_map_police_29dp;
        }
    }

    public void setMarker(int marker) {
        this.marker = marker;
    }
}
