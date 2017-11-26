package sanmateo.com.profileapp.user;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sanmateo.com.profileapp.user.local.RoomUserLocalModule;
import sanmateo.com.profileapp.user.login.LoginModule;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module(includes = {
                       LoginModule.class,
                       RoomUserLocalModule.class
})
public abstract class UserModule {

    @Provides
    @Singleton
    static User provideUser() {
        return new User();
    }
}
