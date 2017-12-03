package sanmateo.com.profileapp.incident.usecase.local.saver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import java.util.List;

import io.reactivex.Observable;
import sanmateo.com.profileapp.factory.IncidentFactory;
import sanmateo.com.profileapp.incident.usecase.Incident;
import sanmateo.com.profileapp.incident.usecase.local.IncidentDao;
import sanmateo.com.profileapp.incident.usecase.remote.mapper.DtoToIncidentMapper;
import sanmateo.com.profileapp.util.RoomTestRule;


import static org.robolectric.annotation.Config.NONE;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = NONE, sdk = 23)
public class DefaultIncidentRoomSaverTest {

    @Rule
    public RoomTestRule roomTestRule = new RoomTestRule();

    private DefaultIncidentRoomSaver classUnderTest;

    private IncidentDao incidentDao;

    @Before
    public void setUp() {
        incidentDao = roomTestRule.getDatabase()
                                  .incidentDao();

        classUnderTest = new DefaultIncidentRoomSaver(incidentDao);
    }

    @Test
    public void savingOfIncidentToLocalWillSucceed() {
        incidentDao.count()
                   .test()
                   .assertValue(0l);

        Incident incident = Observable.just(IncidentFactory.dto())
                                      .compose(new DtoToIncidentMapper())
                                      .blockingFirst();

        classUnderTest.saveIncident(incident).blockingAwait();

        incidentDao.count()
                   .test()
                   .assertValue(1l);
    }


    @Test
    public void savingOfIncidentsToLocalWillSucceed() {
        incidentDao.count()
                   .test()
                   .assertComplete()
                   .assertNoErrors()
                   .assertValue(0l);

        List<Incident> incidents = Observable.fromArray(IncidentFactory.dtos())
                                             .compose(new DtoToIncidentMapper())
                                             .toList()
                                             .blockingGet();

        classUnderTest.saveIncidents(incidents).blockingAwait();

        incidentDao.count()
                   .test()
                   .assertComplete()
                   .assertNoErrors()
                   .assertValue(Long.valueOf(incidents.size()));
    }
}