package sanmateo.com.profileapp.splash.usecase;

import io.reactivex.Single;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 26/11/2017.
 */

public interface SplashLoader {

    Single<User> loadExistingUser();
}
