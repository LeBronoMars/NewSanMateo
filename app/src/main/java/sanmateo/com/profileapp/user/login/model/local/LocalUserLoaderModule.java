package sanmateo.com.profileapp.user.login.model.local;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module
public interface LocalUserLoaderModule {

    @Binds
    abstract LocalUserLoader bindsLocalUserLoader(DefaultLocalUserLoader defaultLocalUserLoader);
}
