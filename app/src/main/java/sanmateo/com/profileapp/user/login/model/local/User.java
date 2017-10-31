package sanmateo.com.profileapp.user.login.model.local;

import io.realm.annotations.PrimaryKey;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public class User {

    public String address;

    public String createdAt;

    public String email;

    public String firstName;

    public String gender;

    @PrimaryKey
    public int id;

    public String lastName;

    public String picUrl;

    public String status;

    public String token;

    public String updatedAt;

    public String userLevel;
}
