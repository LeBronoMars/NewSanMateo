package sanmateo.com.profileapp.user.login.model.local.loader;

import io.reactivex.Single;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultLocalUserLoader implements LocalUserLoader {

    @Override
    public Single<User> loadLocalUser() {
        return null;
    }
}
