package sanmateo.com.profileapp.splash.usecase;

import javax.inject.Inject;

import io.reactivex.Single;
import sanmateo.com.profileapp.user.local.UserDao;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 26/11/2017.
 */

public class DefaultSplashLoader implements SplashLoader {

    private User user;

    private UserDao userDao;

    @Inject
    public DefaultSplashLoader(User user, UserDao userDao) {
        this.user = user;
        this.userDao = userDao;
    }

    @Override
    public Single<User> loadExistingUser() {
        return Single.defer(() -> userDao.findOne()
                                         .flatMapSingle(this::userFrom));
    }

    private Single<User> userFrom(User user) {
        this.user.address = user.address;
        this.user.createdAt = user.createdAt;
        this.user.email = user.email;
        this.user.firstName = user.firstName;
        this.user.gender = user.gender;
        this.user.id = user.id;
        this.user.lastName = user.lastName;
        this.user.picUrl = user.picUrl;
        this.user.status = user.status;
        this.user.token = user.token;
        this.user.updatedAt = user.updatedAt;
        this.user.userLevel = user.userLevel;
        return Single.just(this.user);
    }
}
