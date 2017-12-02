package sanmateo.com.profileapp.user.login.model;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


import static org.mockito.Mockito.mock;

/**
 * Created by rsbulanon on 11/11/2017.
 */
@Module
public abstract class MockLocalUserModule {

    @Provides
    @Singleton
    UserLoader provideMockUserLoader() {
        return mock(UserLoader.class);
    }
}
