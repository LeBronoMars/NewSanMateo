package sanmateo.com.profileapp.incident.usecase.local.saver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import sanmateo.com.profileapp.incident.usecase.Incident;
import sanmateo.com.profileapp.incident.usecase.local.IncidentDao;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DefaultIncidentRoomSaver implements IncidentRoomSaver {

    private IncidentDao incidentDao;

    @Inject
    public DefaultIncidentRoomSaver(IncidentDao incidentDao) {
        this.incidentDao = incidentDao;
    }

    @Override
    public Completable saveIncident(Incident incident) {
        return Completable.defer(() -> {
            incidentDao.insert(incident);
            return Completable.complete();
        });
    }

    @Override
    public Completable saveIncidents(List<Incident> incidents) {
        return Completable.defer(() -> {
            incidentDao.insertAll(incidents);
            return Completable.complete();
        });
    }
}
