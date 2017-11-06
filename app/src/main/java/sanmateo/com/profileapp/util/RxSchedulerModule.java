package sanmateo.com.profileapp.util;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Module
public abstract class RxSchedulerModule {

    @Binds
    abstract RxSchedulerUtils bindSchedulerUtil(DefaultRxSchedulerUtils defaultRxSchedulerUtils);
}
