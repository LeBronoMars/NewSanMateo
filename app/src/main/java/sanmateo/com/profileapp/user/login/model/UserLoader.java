package sanmateo.com.profileapp.user.login.model;

import io.reactivex.Single;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public interface UserLoader {

    Single<User> login(String emal, String password);
}
