package sanmateo.com.profileapp.user.login.model;

import javax.inject.Inject;

import io.reactivex.Single;
import sanmateo.com.profileapp.user.login.model.remote.LoginRemoteAuthenticator;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultUserLoader implements UserLoader {

    private LoginRemoteAuthenticator loginRemoteAuthenticator;

    @Inject
    public DefaultUserLoader(LoginRemoteAuthenticator loginRemoteAuthenticator) {
        this.loginRemoteAuthenticator = loginRemoteAuthenticator;
    }

    @Override
    public Single<User> login(String email, String password) {
        return loginRemoteAuthenticator.login(email, password)
                                       .flatMap(new UserDtoToUserMapper());
    }
}
