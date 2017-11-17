package sanmateo.com.profileapp.user.local;

import javax.inject.Inject;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */

public class DefaultRoomUserLoader implements RoomUserLoader {

    private UserDao userDao;

    @Inject
    public DefaultRoomUserLoader(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Maybe<User> loadCurrentUser() {
        return userDao.findOne();
    }
}
