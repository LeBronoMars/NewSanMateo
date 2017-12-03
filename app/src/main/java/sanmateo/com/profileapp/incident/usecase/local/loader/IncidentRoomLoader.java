package sanmateo.com.profileapp.incident.usecase.local.loader;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.incident.usecase.Incident;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public interface IncidentRoomLoader {

   Single<Long> count();

   Maybe<List<Incident>> loadIncidents(String incidentType);

   Maybe<List<Incident>> loadIncidents();
}
