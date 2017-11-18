package sanmateo.com.profileapp.waterlevel.usecase.remote;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 18/11/2017.
 */

@Module
public interface WaterLevelRemoteModule {

    @Binds
    WaterLevelRemoteLoader bindsWaterLevelRemoteLoader(
        DefaultWaterLevelRemoteLoader defaultWaterLevelRemoteLoader);
}
