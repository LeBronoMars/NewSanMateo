package sanmateo.com.profileapp.helpers;

/**
 * Created by rsbulanon on 10/2/16.
 */

public class AppConstants {

    public static boolean IS_FACEBOOK_APP_INSTALLED = false;

    /** warning messages */
    public static final String WARN_CONNECTION = "Connection error, Check your network connection and try again.";
    public static final String WARN_FIELD_REQUIRED = "This field is required";
    public static final String WARN_PASSWORD_NOT_MATCH = "Password did not match";
    public static final String WARN_INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String WARN_CANT_GET_IMAGE = "We can't get your image. Please try again.";
    public static final String WARN_INVALID_ACCOUNT = "Invalid account!";
    public static final String WARN_INVALID_CONTACT_NO = "Invalid contact no";

    /** aws s3 buckets */
    public static final String BUCKET_ROOT = "sanmateoprofileapp";
    public static final String BUCKET_INCIDENTS = BUCKET_ROOT + "/incidents";
    public static final String BUCKET_PROFILE_PIC = BUCKET_ROOT + "/profilepics";
    public static final String BUCKET_OFFICIALS_PIC = BUCKET_ROOT + "/officials";
    public static final String BUCKET_GALLERY = BUCKET_ROOT + "/gallery";
    public static final String BUCKET_NEWS = BUCKET_ROOT + "/news";

    /** prefs key */
    public static final String PREFS_LOCAL_EMERGENCY_KITS = "local emergency kits";

    /** image url */
    public static final String IMAGE_URL_FLOOD_CAUSE = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/causes_flood.png";
    public static final String IMAGE_URL_FLOOD_BEFORE = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/before_flood.png";
    public static final String IMAGE_URL_FLOOD_DURING = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/during_flood.png";
    public static final String IMAGE_URL_FLOOD_AFTER = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/after_flood.png";
    public static final String IMAGE_URL_TIPS_EMERGENCY_KIT = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/emergencykit.png";
    public static final String IMAGE_URL_TIPS_REMINDERS = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/Reminder.png";
    public static final String IMAGE_URL_EQ_HAZARDS = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/HAZARDS_EQ.png";
    public static final String IMAGE_URL_EQ_GRAPHIC_AID = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/Graphic_AID.png";
    public static final String IMAGE_URL_EQ_BEFORE = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/BEFORE_EQ.png";
    public static final String IMAGE_URL_EQ_DURING = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/DURING_EQ.png";
    public static final String IMAGE_URL_EQ_AFTER = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/AFTER_EQ.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_1 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge1.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_2 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge2.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_3 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge3.png";
    public static final String IMAGE_URL_MARKER_MARKET_1 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic1.png";
    public static final String IMAGE_URL_MARKER_MARKET_2 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic2.png";
    public static final String IMAGE_URL_MARKER_MARKET_3 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic3.png";
}
