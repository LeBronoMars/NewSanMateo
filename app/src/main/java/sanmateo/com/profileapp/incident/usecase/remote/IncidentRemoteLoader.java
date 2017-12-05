package sanmateo.com.profileapp.incident.usecase.remote;

import java.util.List;

import io.reactivex.Single;
import sanmateo.com.profileapp.api.incident.IncidentDto;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentRemoteLoader {

    Single<List<IncidentDto>> loadIncidents(int start, int limit);

    Single<List<IncidentDto>> loadIncidents(int start, int limit, String incidentType);
}
