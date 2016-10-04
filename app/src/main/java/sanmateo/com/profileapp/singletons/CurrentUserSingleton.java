package sanmateo.com.profileapp.singletons;


import sanmateo.com.profileapp.models.response.AuthResponse;

/**
 * Created by rsbulanon on 10/4/16.
 */
public class CurrentUserSingleton {
    private static CurrentUserSingleton CURR_USER = new CurrentUserSingleton();
    private AuthResponse currentUser;

    private CurrentUserSingleton() {}

    public static CurrentUserSingleton newInstance() {
        return CURR_USER;
    }

    public AuthResponse getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AuthResponse currentUser) {
        this.currentUser = null;
        this.currentUser = currentUser;
    }
}
