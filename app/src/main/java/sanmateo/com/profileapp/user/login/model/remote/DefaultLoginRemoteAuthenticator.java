package sanmateo.com.profileapp.user.login.model.remote;

import javax.inject.Inject;

import io.reactivex.Single;
import sanmateo.com.profileapp.api.user.InvalidAccountTransformer;
import sanmateo.com.profileapp.api.user.UserDto;
import sanmateo.com.profileapp.api.user.UserRemoteService;

/**
 * Created by rsbulanon on 31/10/2017.
 */

class DefaultLoginRemoteAuthenticator implements LoginRemoteAuthenticator {

    private UserRemoteService userRemoteService;

    @Inject
    public DefaultLoginRemoteAuthenticator(UserRemoteService userRemoteService) {
        this.userRemoteService = userRemoteService;
    }

    @Override
    public Single<UserDto> login(String email, String password) {
        return userRemoteService.authenticateUser(email, password)
            .compose(new InvalidAccountTransformer<>())
            .flatMap(userDtoResponse -> Single.just(userDtoResponse.body()));
    }
}
