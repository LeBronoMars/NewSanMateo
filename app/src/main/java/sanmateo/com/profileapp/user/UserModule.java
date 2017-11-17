package sanmateo.com.profileapp.user;

import dagger.Module;
import sanmateo.com.profileapp.user.local.RoomUserLocalModule;
import sanmateo.com.profileapp.user.login.LoginModule;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module(includes = {
                       LoginModule.class,
                       RoomUserLocalModule.class
})
public interface UserModule {
}
