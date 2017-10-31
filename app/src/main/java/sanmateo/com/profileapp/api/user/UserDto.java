package sanmateo.com.profileapp.api.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public class UserDto {

    public String address;

    @SerializedName("created_at")
    public String createdAt;

    public String email;

    @SerializedName("first_name")
    public String firstName;

    public String gender;

    public int id;

    @SerializedName("pic_url")
    public String picUrl;

    @SerializedName("last_name")
    public String lastName;

    public String status;

    public String token;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("user_level")
    public String userLevel;

    public UserDto() {
        address = "";
        createdAt = "";
        email = "";
        firstName = "";
        gender = "";
        picUrl = "";
        lastName = "";
        status = "";
        token = "";
        updatedAt = "";
        updatedAt = "";
        userLevel = "";
    }
}
