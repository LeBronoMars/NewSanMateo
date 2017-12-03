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
import static org.robolectric.annotation.Config.NONE;

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

        List<Incident> expected = Observable.fromArray(IncidentFactory.dtos())
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
}