package sanmateo.com.profileapp.incident.usecase.remote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
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
    public Single<List<IncidentDto>> loadIncidents(int start, int limit, String incidentType) {
        return incidentRemoteService.loadIncidents(start, limit, incidentType);
    }

    @Override
    public Single<List<IncidentDto>> loadIncidents(int start, int limit) {
        return incidentRemoteService.loadIncidents(start, limit, null);
    }
}
