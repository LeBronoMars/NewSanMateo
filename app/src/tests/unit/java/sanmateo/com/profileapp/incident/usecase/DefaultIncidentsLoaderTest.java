package sanmateo.com.profileapp.incident.usecase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.incident.IncidentDto;
import sanmateo.com.profileapp.factory.IncidentFactory;
import sanmateo.com.profileapp.incident.usecase.local.loader.IncidentRoomLoader;
import sanmateo.com.profileapp.incident.usecase.local.saver.IncidentRoomSaver;
import sanmateo.com.profileapp.incident.usecase.remote.IncidentRemoteLoader;
import sanmateo.com.profileapp.incident.usecase.remote.mapper.DtoToIncidentMapper;
import sanmateo.com.profileapp.user.local.NoQueryResultException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static sanmateo.com.profileapp.factory.IncidentFactory.dtos;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultIncidentsLoaderTest {

    @Mock
    IncidentRemoteLoader incidentRemoteLoader;

    @Mock
    IncidentRoomLoader incidentRoomLoader;

    @Mock
    IncidentRoomSaver incidentRoomSaver;

    @InjectMocks
    private DefaultIncidentsLoader classUnderTest;

    @Test
    public void loadingOfIncidentsFromApiWillSucceed() {
        IncidentDto[] expected = dtos();

        given(incidentRemoteLoader.loadIncidents(anyInt(), anyInt()))
            .willReturn(Observable.fromArray(expected));

        given(incidentRoomLoader.loadIncidents())
            .willReturn(Maybe.error(NoQueryResultException::new));

        given(incidentRoomSaver.saveIncident(any(Incident.class))).willReturn(Completable
                                                                                  .complete());

        TestObserver<List<Incident>> testObserver = new TestObserver<>();

        classUnderTest.loadIncidents(0, 10)
                      .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isNotZero();

        List<Incident> actual = testObserver.values().get(0);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected[i]);
        }
    }

    @Test
    public void loadingOfIncidentsFromLocalWillSucceed() {
        List<Incident> expected = Observable.fromArray(IncidentFactory.dtos())
                                            .compose(new DtoToIncidentMapper())
                                            .toList()
                                            .blockingGet();

        given(incidentRemoteLoader.loadIncidents(anyInt(), anyInt()))
            .willReturn(Observable.error(new Throwable()));

        given(incidentRoomSaver.saveIncident(any(Incident.class)))
            .willReturn(Completable.error(new Throwable()));

        given(incidentRoomLoader.loadIncidents()).willReturn(Maybe.just(expected));

        classUnderTest.loadIncidents(0, 10)
                      .test()
                      .assertNoErrors()
                      .assertComplete()
                      .assertValue(expected);

    }

    @Test
    public void loadingOfIncidentsWillFailIfApiFailsAndThereIsNoCachedRecords() {
        given(incidentRemoteLoader.loadIncidents(anyInt(), anyInt()))
            .willReturn(Observable.error(new Throwable()));

        given(incidentRoomSaver.saveIncident(any(Incident.class)))
            .willReturn(Completable.error(new Throwable()));

        given(incidentRoomLoader.loadIncidents())
            .willReturn(Maybe.error(NoQueryResultException::new));

        classUnderTest.loadIncidents(0, 10)
                      .test()
                      .assertNotComplete()
                      .assertError(NoQueryResultException.class);
    }
}