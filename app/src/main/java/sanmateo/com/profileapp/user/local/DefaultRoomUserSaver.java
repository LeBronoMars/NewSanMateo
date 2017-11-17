package sanmateo.com.profileapp.user.local;

import javax.inject.Inject;

import io.reactivex.Completable;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */

public class DefaultRoomUserSaver implements RoomUserSaver {

    UserDao userDao;

    @Inject
    public DefaultRoomUserSaver(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Completable saveUser(User user) {
        return Completable.fromCallable(() -> {
                userDao.insert(user);
                return Completable.complete();
            });
    }
}
