package sanmateo.com.profileapp.user.local;

import io.reactivex.Completable;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */

public interface RoomUserSaver {

    Completable saveUser(User user);
}
