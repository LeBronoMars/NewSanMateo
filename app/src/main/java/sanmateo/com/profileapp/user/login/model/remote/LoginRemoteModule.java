package sanmateo.com.profileapp.user.login.model.remote;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module
public interface LoginRemoteModule {

    @Binds
    LoginRemoteAuthenticator bindsLoginRemoteAuthenticator(
        DefaultLoginRemoteAuthenticator defaultLoginRemoteAuthenticator);
}
