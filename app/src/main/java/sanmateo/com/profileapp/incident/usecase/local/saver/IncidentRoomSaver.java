package sanmateo.com.profileapp.incident.usecase.local.saver;

import java.util.List;

import io.reactivex.Completable;
import sanmateo.com.profileapp.incident.usecase.Incident;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentRoomSaver {

    Completable saveIncident(Incident incident);

    Completable saveIncidents(List<Incident> incidents);
}
