package sanmateo.com.profileapp.incident.usecase.remote;

import io.reactivex.Observable;
import sanmateo.com.profileapp.api.incident.IncidentDto;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentRemoteLoader {

    Observable<IncidentDto> loadIncidents(int start, int limit);
}
