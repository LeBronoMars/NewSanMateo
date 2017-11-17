package sanmateo.com.profileapp.user.local;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 17/11/2017.
 */

@Module
public abstract class RoomUserLocalModule {

    @Binds
    abstract RoomUserLoader provideRoomUserLoader(DefaultRoomUserLoader defaultRoomUserLoader);

    @Binds
    abstract RoomUserSaver provideRoomUserSaver(DefaultRoomUserSaver defaultRoomUserSaver);
}
