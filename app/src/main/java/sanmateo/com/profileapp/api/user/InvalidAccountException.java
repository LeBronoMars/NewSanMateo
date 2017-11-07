package sanmateo.com.profileapp.api.user;

/**
 * Thrown when a stream for API request fails and receives an error code
 * {@link HttpURLConnection#HTTP_UNAUTHORIZED}.
 */
public class InvalidAccountException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Failed logging in.";

    public InvalidAccountException() {
        super(ERROR_MESSAGE);
    }
}
