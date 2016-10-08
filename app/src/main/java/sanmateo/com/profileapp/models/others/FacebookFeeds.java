package sanmateo.com.profileapp.models.others;

/**
 * Created by rsbulanon on 9/3/16.
 */
public class FacebookFeeds {

    private String message;
    private String datePosted;

    public FacebookFeeds(String message, String datePosted) {
        this.message = message;
        this.datePosted = datePosted;
    }

    public String getMessage() {
        return message;
    }

    public String getDatePosted() {
        return datePosted;
    }
}
