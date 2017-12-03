package sanmateo.com.profileapp.incident.usecase.local.loader;

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


import static java.lang.Long.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.annotation.Config.NONE;
import static sanmateo.com.profileapp.factory.IncidentFactory.dtos;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = NONE, sdk = 23)
public class DefaultIncidentRoomLoaderTest {

    @Rule
    public RoomTestRule roomTestRule = new RoomTestRule();

    private DefaultIncidentRoomLoader classUnderTest;

    private IncidentDao incidentDao;

    @Before
    public void setUp() {
        incidentDao = roomTestRule.getDatabase().incidentDao();

        classUnderTest = new DefaultIncidentRoomLoader(incidentDao);
    }

    @Test
    public void countShouldReturnZeroByDefault() {
        classUnderTest.count()
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(0l);
    }

    @Test
    public void loadingOfLocalIncidentsWillSucceed() {
        countShouldReturnZeroByDefault();

        List<Incident> expected = Observable.fromArray(dtos())
                                            .compose(new DtoToIncidentMapper())
                                            .toList()
                                            .blockingGet();

        incidentDao.insertAll(expected);

        classUnderTest.count()
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(valueOf(expected.size()));
    }

    @Test
    public void loadingOfLocalIncidentsByTypeWillSucceed() {
        String fireIncidentType = "fire incident";
        String trafficIncidentType = "traffic incident";

        List<Incident> fireIncidents = Observable.fromArray(dtos())
                                            .compose(new DtoToIncidentMapper())
                                            .toList()
                                            .blockingGet();

        for (Incident incident : fireIncidents) {
            incident.incidentType = fireIncidentType;
        }

        List<Incident> trafficIncidents = Observable.fromArray(dtos())
                                            .compose(new DtoToIncidentMapper())
                                            .toList()
                                            .blockingGet();

        for (Incident incident : trafficIncidents) {
            incident.incidentType = trafficIncidentType;
        }

        incidentDao.insertAll(fireIncidents);
        incidentDao.insertAll(trafficIncidents);

        // must fetch only incidents whose type is traffic
        List<Incident> actual = classUnderTest.loadIncidents(trafficIncidentType)
                                              .blockingGet();

        for (Incident incident : actual) {
            assertThat(incident.incidentType).isEqualTo(trafficIncidentType);
        }
    }
}