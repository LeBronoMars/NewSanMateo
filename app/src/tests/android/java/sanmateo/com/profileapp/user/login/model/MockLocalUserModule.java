package sanmateo.com.profileapp.user.login.model;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sanmateo.com.profileapp.user.login.model.local.loader.LocalUserLoader;


import static org.mockito.Mockito.mock;

/**
 * Created by rsbulanon on 11/11/2017.
 */
@Module
public abstract class MockLocalUserModule {

    @Provides
    @Singleton
    LocalUserLoader provideMockLocalUserModule() {
        return mock(LocalUserLoader.class);
    }
}
