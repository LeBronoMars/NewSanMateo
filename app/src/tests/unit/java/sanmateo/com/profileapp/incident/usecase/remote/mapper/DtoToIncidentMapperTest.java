package sanmateo.com.profileapp.incident.usecase.remote.mapper;

import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.incident.IncidentDto;
import sanmateo.com.profileapp.incident.usecase.Incident;


import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.IncidentFactory.dtos;

/**
 * Created by rsbulanon on 03/12/2017.
 */
public class DtoToIncidentMapperTest {

    @Test
    public void apply() {
        IncidentDto[] expected = dtos();

        TestObserver<List<Incident>> testObserver = new TestObserver<>();

        Observable.fromArray(expected)
                  .compose(new DtoToIncidentMapper())
                  .toList()
                  .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isNotZero();

        List<Incident> actual = testObserver.values().get(0);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected[i]);
        }
    }
}