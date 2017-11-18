package sanmateo.com.profileapp.waterlevel;

import dagger.Module;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelModule;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteModule;

@Module(
    includes = {
                   WaterLevelRemoteModule.class,
                   RoomWaterLevelModule.class
    }
)
public abstract class WaterLevelModule {
}
