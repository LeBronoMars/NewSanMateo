package sanmateo.com.profileapp.user.login.model.local.saver;

import io.reactivex.Completable;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultLocalUserSaver implements LocalUserSaver {

    @Override
    public Completable saveUser(User user) {
        return null;
    }
}
