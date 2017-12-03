package sanmateo.com.profileapp.incident.usecase.remote;

import javax.inject.Inject;

import io.reactivex.Observable;
import sanmateo.com.profileapp.api.incident.IncidentDto;
import sanmateo.com.profileapp.api.incident.IncidentRemoteService;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DefaultIncidentRemoteLoader implements IncidentRemoteLoader {

    private IncidentRemoteService incidentRemoteService;

    @Inject
    public DefaultIncidentRemoteLoader(IncidentRemoteService incidentRemoteService) {
        this.incidentRemoteService = incidentRemoteService;
    }

    @Override
    public Observable<IncidentDto> loadIncidents(int start, int limit) {
        return incidentRemoteService.loadIncidents(start, limit);
    }
}
