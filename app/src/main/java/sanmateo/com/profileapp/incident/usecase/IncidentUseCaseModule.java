package sanmateo.com.profileapp.incident.usecase;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.incident.usecase.local.IncidentRoomModule;
import sanmateo.com.profileapp.incident.usecase.remote.IncidentRemoteModule;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@Module(includes = {
                       IncidentRemoteModule.class,
                       IncidentRoomModule.class
})
public interface IncidentUseCaseModule {

    @Binds
    IncidentsLoader bindIncidentLoader(DefaultIncidentsLoader defaultIncidentsLoader);
}
