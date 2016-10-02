package sanmateo.com.profileapp.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class AuthResponse {

    private int id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    @SerializedName("first_name") private String firstName;
    @SerializedName("last_name") private String lastName;
    private String email;
    private String address;
    @SerializedName("contact_no") private String contactNo;
    private String status;
    @SerializedName("user_level") private String userLevel;
    private String gender;
    @SerializedName("pic_url") private String picUrl;
    private String token;

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getStatus() {
        return status;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public String getGender() {
        return gender;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getToken() {
        return token;
    }
}
