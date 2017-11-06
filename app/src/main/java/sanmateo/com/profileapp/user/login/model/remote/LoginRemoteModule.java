package sanmateo.com.profileapp.user.login.model.remote;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module
public interface LoginRemoteModule {

    @Binds
    abstract LoginRemoteAuthenticator bindsLoginRemoteAuthenticator(
        DefaultLoginRemoteAuthenticator defaultLoginRemoteAuthenticator);
}
