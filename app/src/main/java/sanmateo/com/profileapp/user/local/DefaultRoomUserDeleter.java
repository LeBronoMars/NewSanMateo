package sanmateo.com.profileapp.user.local;

import javax.inject.Inject;

import io.reactivex.Completable;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */

public class DefaultRoomUserDeleter implements RoomUserDeleter {

    private UserDao userDao;

    @Inject
    public DefaultRoomUserDeleter(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Completable delete(User user) {
        return Completable.fromCallable(() -> {
            userDao.delete(user);
            return Completable.complete();
        });
    }
}
