package sanmateo.com.profileapp.incident.usecase;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentsLoader {

    Single<List<Incident>> loadIncidents(int start, int limit);

    Single<List<Incident>> loadIncidentsByIncidentType(int start, int limit, String incidentType);
}
