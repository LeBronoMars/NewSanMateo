package sanmateo.com.profileapp.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsbulanon on 17/12/2017.
 */

public class Weather {

    public int id;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String udpatedAt;

    @SerializedName("deleted_at")
    public String deletedAt;

    @SerializedName("background_image")
    public String backgroundImage;

    @SerializedName("heat_index")
    public String heatIndex;

    public String summary;

    @SerializedName("weather_icon")
    public String weatherIcon;

    @SerializedName("humidity")
    public String humidity;

    @SerializedName("uv_index")
    public String uvIndex;

    @SerializedName("wind_speed")
    public String windSpeed;

    @SerializedName("cloud_cover")
    public String cloudCover;

    @SerializedName("winds_from")
    public String windsFrom;

    @SerializedName("wind_gusts")
    public String windGusts;

    @SerializedName("dew_point")
    public String dewPoint;

    public String pressure;

    public String visibility;

    public String temperature;
}
