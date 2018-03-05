package sanmateo.com.profileapp.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 03/01/2018.
 */

public class StormWatch {

    public int id;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String udpatedAt;

    @SerializedName("deleted_at")
    public String deletedAt;

    public String image;

    public String summary;
}
