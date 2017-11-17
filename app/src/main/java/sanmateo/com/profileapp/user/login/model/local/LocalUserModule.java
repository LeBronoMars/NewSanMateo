package sanmateo.com.profileapp.user.login.model.local;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.user.login.model.local.loader.DefaultLocalUserLoader;
import sanmateo.com.profileapp.user.login.model.local.loader.LocalUserLoader;
import sanmateo.com.profileapp.user.login.model.local.saver.DefaultLocalUserSaver;
import sanmateo.com.profileapp.user.login.model.local.saver.LocalUserSaver;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module
public interface LocalUserModule {

    @Binds
    abstract LocalUserLoader bindsLocalUserLoader(DefaultLocalUserLoader defaultLocalUserLoader);

    //@Binds
    //abstract LocalUserSaver bindsLocalUserSaver(DefaultLocalUserSaver defaultLocalUserSaver);
}
