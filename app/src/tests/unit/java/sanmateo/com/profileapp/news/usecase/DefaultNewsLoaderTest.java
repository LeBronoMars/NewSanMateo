package sanmateo.com.profileapp.news.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.news.local.loader.RoomNewsLoader;
import sanmateo.com.profileapp.news.local.saver.RoomNewsSaver;
import sanmateo.com.profileapp.news.usecase.remote.NewsRemoteLoader;
import sanmateo.com.profileapp.news.usecase.remote.mapper.DtoToNewsMapper;
import sanmateo.com.profileapp.user.local.NoQueryResultException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static sanmateo.com.profileapp.factory.NewsFactory.dtos;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultNewsLoaderTest {

    @Mock
    NewsRemoteLoader remoteLoader;

    @Mock
    RoomNewsLoader roomNewsLoader;

    @Mock
    RoomNewsSaver roomNewsSaver;

    private DefaultNewsLoader classUnderTest;

    private TestObserver<List<News>> testObserver = new TestObserver<>();

    @Before
    public void setUp() {
        classUnderTest = new DefaultNewsLoader(remoteLoader, roomNewsLoader, roomNewsSaver);
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
    public void loadingOfNewsFromApiWillSucceed() {
        NewsDto[] expected = dtos();

        given(remoteLoader.loadNews(anyInt(), anyInt())).willReturn(Observable.fromArray(expected));

        given(roomNewsSaver.saveNews(any(News.class))).willReturn(Completable.complete());

        given(roomNewsLoader.loadNews()).willReturn(Maybe.just(new ArrayList<>()));

        classUnderTest.loadNews(0, 10)
                      .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isNotZero();

        List<News> actual = testObserver.values().get(0);

        assertThat(actual.size()).isEqualTo(expected.length);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i))
                .isEqualToComparingFieldByField(expected[i]);
        }
    }

    @Test
    public void loadingOfNewsFromLocalIfApiCallFails() {
        List<News> expected = Observable.fromArray(dtos())
                                        .compose(new DtoToNewsMapper())
                                        .toList()
                                        .blockingGet();

        given(remoteLoader.loadNews(anyInt(), anyInt()))
            .willReturn(Observable.error(new Throwable()));

        given(roomNewsSaver.saveNews(any(News.class)))
            .willReturn(Completable.error(new Throwable()));

        given(roomNewsLoader.loadNews()).willReturn(Maybe.just(expected));

        classUnderTest.loadNews(0, 10)
                      .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isNotZero();

        List<News> actual = testObserver.values().get(0);

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected.get(i));
        }
    }

    @Test
    public void loadingOfNewsWillReturnEmptyIfApiCallFailsAndThereIsNoCachedNews() {
        given(remoteLoader.loadNews(anyInt(),
                                    anyInt())).willReturn(Observable.error(new Throwable()));

        given(roomNewsLoader.loadNews()).willReturn(Maybe.error(NoQueryResultException::new));

        given(roomNewsSaver.saveNews(any(News.class)))
            .willReturn(Completable.error(new Throwable()));

        classUnderTest.loadNews(0, 10)
                      .subscribe(testObserver);

        testObserver.assertNotComplete();
        testObserver.assertError(NoQueryResultException.class);
    }
}