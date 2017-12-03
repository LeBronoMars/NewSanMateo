package sanmateo.com.profileapp.incident.usecase.local;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.incident.usecase.local.loader.DefaultIncidentRoomLoader;
import sanmateo.com.profileapp.incident.usecase.local.loader.IncidentRoomLoader;
import sanmateo.com.profileapp.incident.usecase.local.saver.DefaultIncidentRoomSaver;
import sanmateo.com.profileapp.incident.usecase.local.saver.IncidentRoomSaver;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@Module
public interface IncidentRoomModule {

    @Binds IncidentRoomLoader bindIncidentRoomLoader(
        DefaultIncidentRoomLoader defaultIncidentRoomLoader);

    @Binds IncidentRoomSaver bindIncidentRoomSaver(
        DefaultIncidentRoomSaver defaultIncidentRoomSaver);
}
