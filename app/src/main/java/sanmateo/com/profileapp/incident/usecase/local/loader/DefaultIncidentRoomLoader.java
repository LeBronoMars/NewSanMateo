package sanmateo.com.profileapp.incident.usecase.local.loader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.incident.usecase.Incident;
import sanmateo.com.profileapp.incident.usecase.local.IncidentDao;
import sanmateo.com.profileapp.user.local.NoQueryResultException;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DefaultIncidentRoomLoader implements IncidentRoomLoader {

    private IncidentDao incidentDao;

    @Inject
    public DefaultIncidentRoomLoader(IncidentDao incidentDao) {
        this.incidentDao = incidentDao;
    }

    @Override
    public Single<Long> count() {
        return Single.defer(() -> incidentDao.count());
    }

    @Override
    public Maybe<List<Incident>> loadIncidents() {
        return Maybe.defer(() -> incidentDao.findAll()
                                            .switchIfEmpty(Maybe.error(
                                                NoQueryResultException::new)));
    }

    @Override
    public Maybe<List<Incident>> loadIncidents(String incidentType) {
        return Maybe.defer(() -> incidentDao.findByIncidentType(incidentType)
                                                   .switchIfEmpty(Maybe.error(
                                                       NoQueryResultException::new)));
    }
}
