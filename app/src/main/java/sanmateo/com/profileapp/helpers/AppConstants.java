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
    public static final String WARN_SELECT_GENDER = "Please specifiy your gender!";
    public static final String WARN_UNABLE_TO_FIND_APP = "Unavailable to find this app in Google Playstore market";
    public static final String WARN_CONNECTION_NEW = "You are offline. Please connect to the internet and try again.";
    public static final String WARN_INCORRECT_ACCOUNT = "Incorrect username or password.";
    public static final String WARN_OFFLINE = "You are offline.";

    /** aws s3 buckets */
    public static final String BUCKET_ROOT = "sanmateoprofileapp";
    public static final String BUCKET_INCIDENTS = BUCKET_ROOT + "/incidents";
    public static final String BUCKET_PROFILE_PIC = BUCKET_ROOT + "/profilepics";

    /** prefs key */
    public static final String PREFS_LOCAL_EMERGENCY_KITS = "local emergency kits";

    /** image url */
    public static final String IMAGE_URL_MARKER_BRIDGE_1 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge1.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_2 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge2.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_3 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge3.png";
    public static final String IMAGE_URL_MARKER_MARKET_1 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic1.png";
    public static final String IMAGE_URL_MARKER_MARKET_2 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic2.png";
    public static final String IMAGE_URL_MARKER_MARKET_3 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic3.png";
    public static final String IMAGE_URL_CPR_ADULT = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/CPR/CPR_adult.png";
    public static final String IMAGE_URL_CPR_INFANT = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/CPR/CPR_infant_small.png";
    public static final String IMAGE_URL_CPR_AMERICAN_HEART = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/CPR/american_heart.png";
    public static final String IMAGE_URL_ISANG_PAALALA = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/paalala_small.png";

    public static String SAN_MATEO_LOGO = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/banners/san_mateo_logo.png";

    /** categories */
    public static final String CATEGORY_PEACE_AND_SECURITY = "Peace & Security";
    public static final String CATEGORY_SOCIAL_AND_DEVELOPMENT = "Social & Development Sector";
    public static final String CATEGORY_HEALTH = "Health Sector";
    public static final String CATEGORY_RESORT = "Resort";
    public static final String CATEGORY_SHOPPING_MALL = "Shopping Mall";
    public static final String CATEGORY_AUTHENTIC_RESTAURANT = "Authentic Restaurant";
    public static final String CATEGORY_HERITAGE_SITE = "Heritage Site";
    public static final String CATEGORY_MUNICIPAL = "Municipal Government";
    public static final String CATEGORY_BARANGAY = "Barangay Government";
}
