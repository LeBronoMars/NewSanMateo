package sanmateo.com.profileapp.user.login.model;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.user.login.model.remote.LoginRemoteModule;

/**
 * Created by rsbulanon on 06/11/2017.
 */

@Module(includes = {
                       LoginRemoteModule.class
})
public abstract class LoginModelModule {

    @Binds
    abstract UserLoader bindsUserLoader(DefaultUserLoader defaultUserLoader);

}
