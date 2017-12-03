package sanmateo.com.profileapp.incident.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import sanmateo.com.profileapp.incident.usecase.local.loader.IncidentRoomLoader;
import sanmateo.com.profileapp.incident.usecase.local.saver.IncidentRoomSaver;
import sanmateo.com.profileapp.incident.usecase.remote.IncidentRemoteLoader;
import sanmateo.com.profileapp.incident.usecase.remote.mapper.DtoToIncidentMapper;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DefaultIncidentsLoader implements IncidentsLoader {

    private IncidentRoomLoader incidentRoomLoader;

    private IncidentRoomSaver incidentRoomSaver;

    private IncidentRemoteLoader incidentRemoteLoader;

    @Inject
    public DefaultIncidentsLoader(IncidentRoomLoader incidentRoomLoader,
                                  IncidentRoomSaver incidentRoomSaver,
                                  IncidentRemoteLoader incidentRemoteLoader) {
        this.incidentRoomLoader = incidentRoomLoader;
        this.incidentRoomSaver = incidentRoomSaver;
        this.incidentRemoteLoader = incidentRemoteLoader;
    }

    @Override
    public Single<List<Incident>> loadIncidents(int start, int limit) {
        return incidentRemoteLoader.loadIncidents(start, limit)
                                   .compose(new DtoToIncidentMapper())
                                   .flatMapSingle(this::saveToLocal)
                                   .onErrorResumeNext(loadFromLocal())
                                   .toList();
    }

    @Override
    public Single<List<Incident>> loadIncidentsByIncidentType(int start,
                                                              int limit,
                                                              String incidentType) {
        return incidentRemoteLoader.loadIncidents(start, limit, incidentType)
                                   .compose(new DtoToIncidentMapper())
                                   .flatMapSingle(this::saveToLocal)
                                   .onErrorResumeNext(loadFromLocal())
                                   .toList();

    }

    public Observable<Incident> loadFromLocal() {
        return incidentRoomLoader.loadIncidents()
                                 .flatMapObservable(Observable::fromIterable);
    }

    private Single<Incident> saveToLocal(Incident incident) {
        return incidentRoomSaver.saveIncident(incident)
                                .andThen(Single.just(incident));
    }
}
