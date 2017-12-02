package sanmateo.com.profileapp.news.local;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.news.local.loader.DefaultRoomNewsLoader;
import sanmateo.com.profileapp.news.local.loader.RoomNewsLoader;
import sanmateo.com.profileapp.news.local.saver.DefaultRoomNewsSaver;
import sanmateo.com.profileapp.news.local.saver.RoomNewsSaver;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@Module
public interface RoomNewsModule {

    @Binds
    RoomNewsLoader bindRoomNewsLoader(DefaultRoomNewsLoader defaulRoomNewsLoader);

    @Binds
    RoomNewsSaver bindRoomNewsSaver(DefaultRoomNewsSaver defaultRoomNewsSaver);
}
