package sanmateo.com.profileapp.user.login.model.local.loader;

import javax.inject.Inject;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.util.realm.RealmUtil;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultLocalUserLoader implements LocalUserLoader {

    private RealmUtil<User> userRealmUtil;

    @Inject
    public DefaultLocalUserLoader(RealmUtil<User> userRealmUtil) {
        this.userRealmUtil = userRealmUtil;
    }

    @Override
    public Maybe<User> loadLocalUser() {
        return userRealmUtil.first();
    }
}
