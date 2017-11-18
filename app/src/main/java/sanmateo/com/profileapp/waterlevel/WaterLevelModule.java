package sanmateo.com.profileapp.waterlevel;

import dagger.Module;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelModule;

@Module(
    includes = {
                   RoomWaterLevelModule.class
    }
)
public abstract class WaterLevelModule {
}
