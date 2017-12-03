package sanmateo.com.profileapp.incident;

import dagger.Module;
import sanmateo.com.profileapp.incident.list.IncidentListModule;
import sanmateo.com.profileapp.incident.usecase.IncidentUseCaseModule;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@Module(includes = {
                       IncidentUseCaseModule.class,
                       IncidentListModule.class
})
public interface IncidentModule {

}
