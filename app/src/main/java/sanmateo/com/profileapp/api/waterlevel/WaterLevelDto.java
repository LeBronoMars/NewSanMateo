package sanmateo.com.profileapp.api.waterlevel;

import com.google.gson.annotations.SerializedName;

public class WaterLevelDto {

    public String alert;

    public String area;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("deleted_at")
    public String deletedAt;

    public int id;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("water_level")
    public float waterLevel;

    public WaterLevelDto() {
        this.alert = "";
        this.area = "";
        this.createdAt = "";
        this.deletedAt = "";
        this.updatedAt = "";
    }
}
