package sanmateo.com.profileapp.incident.usecase.remote;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 03/12/2017.
 */

@Module
public interface IncidentRemoteModule {

    @Binds
    IncidentRemoteLoader bindIncidentRemoteLoader(
        DefaultIncidentRemoteLoader defaultIncidentRemoteLoader);
}
