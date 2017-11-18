package sanmateo.com.profileapp.user.local;

public class NoQueryResultException extends RuntimeException {

    private static final String ERROR_MESSAGE = "No result found.";

    public NoQueryResultException() {
        super(ERROR_MESSAGE);
    }
}
