package sanmateo.com.profileapp.news.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.news.usecase.remote.NewsRemoteLoader;


import static java.util.Calendar.JANUARY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static sanmateo.com.profileapp.factory.NewsFactory.dtos;
import static sanmateo.com.profileapp.util.TimeUtils.date;
import static sanmateo.com.profileapp.util.TimeUtils.toDate;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultNewsLoaderTest {

    @Mock
    NewsRemoteLoader remoteLoader;

    private DefaultNewsLoader classUnderTest;

    private TestObserver<List<News>> testObserver = new TestObserver<>();

    @Before
    public void setUp() {
        classUnderTest = new DefaultNewsLoader(remoteLoader);
    }

    @Test
    public void loadingOfNewsWillFail() {
        given(remoteLoader.loadNews(anyInt(), anyInt()))
            .willReturn(Observable.error(new Throwable()));

        classUnderTest.loadNews(0, 10)
                      .subscribe(testObserver);

        testObserver.assertNotComplete();
        testObserver.assertError(Throwable.class);

        assertThat(testObserver.valueCount()).isZero();
    }

    @Test
    public void loadingOfNewsWillSucceed() {
        NewsDto[] expected = dtos();

        given(remoteLoader.loadNews(anyInt(), anyInt())).willReturn(Observable.fromArray(expected));

        classUnderTest.loadNews(0, 10)
                      .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isNotZero();

        List<News> actual = testObserver.values().get(0);

        assertThat(actual.size()).isEqualTo(expected.length);

        // assert that News are sorted descending via News#updatedAt
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i))
                .isEqualToComparingFieldByField(expected[i]);
        }
    }
}