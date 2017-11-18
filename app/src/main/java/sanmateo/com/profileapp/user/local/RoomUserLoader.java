package sanmateo.com.profileapp.user.local;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */

public interface RoomUserLoader {

    Maybe<User> findByEmail(String email);

    Maybe<User> loadCurrentUser();

}
