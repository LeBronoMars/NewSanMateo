package sanmateo.com.profileapp.waterlevel.usecase;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelModule;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteModule;

/**
 * Created by rsbulanon on 19/11/2017.
 */
@Module(includes = {
                       RoomWaterLevelModule.class,
                       WaterLevelRemoteModule.class,
})
public abstract class WaterLevelUseCaseModule {

    @Binds
    abstract WaterLevelLoader bindsWaterLevelLoader(
        DefaultWaterLevelLoader defaultWaterLevelLoader);
}
