package sanmateo.com.profileapp.util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Module
public abstract class RxSchedulerModule {

    @Provides
    @Singleton
    static RxSchedulerUtils provideRxSchedulerUtils() {
        return new DefaultRxSchedulerUtils();
    }
}
