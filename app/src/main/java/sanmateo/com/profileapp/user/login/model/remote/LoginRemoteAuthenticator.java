package sanmateo.com.profileapp.user.login.model.remote;

import io.reactivex.Single;
import sanmateo.com.profileapp.api.user.UserDto;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public interface LoginRemoteAuthenticator {

    Single<UserDto> login(String email, String password);
}
