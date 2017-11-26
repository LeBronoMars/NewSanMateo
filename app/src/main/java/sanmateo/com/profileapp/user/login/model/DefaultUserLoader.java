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

    private User user;

    @Inject
    public DefaultUserLoader(LoginRemoteAuthenticator loginRemoteAuthenticator,
                             RoomUserDeleter roomUserDeleter,
                             RoomUserLoader roomUserLoader,
                             RoomUserSaver roomUserSaver,
                             User user) {
        this.loginRemoteAuthenticator = loginRemoteAuthenticator;
        this.roomUserDeleter = roomUserDeleter;
        this.roomUserLoader = roomUserLoader;
        this.roomUserSaver = roomUserSaver;
        this.user = user;
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
                                                   .toSingle())
                                       .flatMap(this::userFrom);
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
