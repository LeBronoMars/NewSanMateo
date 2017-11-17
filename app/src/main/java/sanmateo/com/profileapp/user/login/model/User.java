package sanmateo.com.profileapp.user.login.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Entity
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
