package sanmateo.com.profileapp.user.login.model;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.user.local.RoomUserDeleter;
import sanmateo.com.profileapp.user.local.RoomUserLoader;
import sanmateo.com.profileapp.user.local.RoomUserSaver;
import sanmateo.com.profileapp.user.login.model.remote.LoginRemoteAuthenticator;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultUserLoader implements UserLoader {

    private RoomUserDeleter roomUserDeleter;

    private RoomUserLoader roomUserLoader;

    private LoginRemoteAuthenticator loginRemoteAuthenticator;

    private RoomUserSaver roomUserSaver;

    @Inject
    public DefaultUserLoader(LoginRemoteAuthenticator loginRemoteAuthenticator,
                             RoomUserDeleter roomUserDeleter,
                             RoomUserLoader roomUserLoader,
                             RoomUserSaver roomUserSaver) {
        this.loginRemoteAuthenticator = loginRemoteAuthenticator;
        this.roomUserDeleter = roomUserDeleter;
        this.roomUserLoader = roomUserLoader;
        this.roomUserSaver = roomUserSaver;
    }

    @Override
    public Single<User> login(String email, String password) {
        return loginRemoteAuthenticator.login(email, password)
                                       .flatMap(new UserDtoToUserMapper())
                                       .flatMap(
                                           user ->
                                               deleteExistingLocalUser(user)
                                                   .andThen(insertNewLocalUser(user))
                                                   .andThen(checkForExistingUserFromLocal())
                                                   .toSingle());
    }

    private Maybe<User> checkForExistingUserFromLocal() {
        return roomUserLoader.loadCurrentUser();
    }

    private Completable deleteExistingLocalUser(User user) {
        return roomUserDeleter.delete(user);
    }

    private Completable insertNewLocalUser(User user) {
        return roomUserSaver.saveUser(user);
    }
}
