package sanmateo.com.profileapp.waterlevel.local;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RoomWaterLevelModule {

    @Binds
    abstract RoomWaterLevelLoader bindsWaterLevelLocalLoader(
        DefaultRoomWaterLevelLoader defaultWaterLevelLocalLoader);
}
