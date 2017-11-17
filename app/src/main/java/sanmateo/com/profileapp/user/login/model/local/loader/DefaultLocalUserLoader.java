package sanmateo.com.profileapp.user.login.model.local.loader;

import javax.inject.Inject;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.user.local.RoomUserLoader;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultLocalUserLoader implements LocalUserLoader {

    RoomUserLoader roomUserLoader;

    @Inject
    public DefaultLocalUserLoader(RoomUserLoader roomUserLoader) {
        this.roomUserLoader = roomUserLoader;
    }

    @Override
    public Maybe<User> loadLocalUser() {
        return roomUserLoader.loadCurrentUser();
    }
}
