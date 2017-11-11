package sanmateo.com.profileapp.util.rx;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rsbulanon on 11/11/2017.
 */

@Module
public class MockRxSchedulerModule {

    @Provides
    @Singleton
    static RxSchedulerUtils provideRxSchedulerUtils() {
        return Mockito.spy(new DefaultRxSchedulerUtils());
    }
}
