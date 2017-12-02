package sanmateo.com.profileapp.news.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sanmateo.com.profileapp.news.usecase.News;
import sanmateo.com.profileapp.news.usecase.NewsLoader;
import sanmateo.com.profileapp.news.usecase.remote.mapper.DtoToNewsMapper;
import sanmateo.com.profileapp.news.view.NewsView;
import sanmateo.com.profileapp.user.local.NoQueryResultException;
import sanmateo.com.profileapp.util.TestableRxSchedulerUtil;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static sanmateo.com.profileapp.factory.NewsFactory.dtos;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultNewsPresenterTest {

    @Mock
    NewsView view;

    @Mock
    NewsLoader newsLoader;

    @Spy
    TestableRxSchedulerUtil rxSchedulerUtil;

    private DefaultNewsPresenter classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new DefaultNewsPresenter(newsLoader, rxSchedulerUtil);
    }

    @Test
    public void attachView() {
        classUnderTest.attachView(view);
        assertThat(classUnderTest.view).isSameAs(view);
    }

    @Test
    public void loadingOfNewsWillFail() {
        attachView();

        given(newsLoader.loadNews(anyInt(), anyInt())).willReturn(Single.error(
            NoQueryResultException::new));

        int expectedStart = 0;
        int expectedLimit = 10;

        classUnderTest.loadNews(expectedStart, expectedLimit);

        verify(view).showProgress();
        verify(view).hideProgress();

        ArgumentCaptor<Integer> startArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> limitArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(newsLoader).loadNews(startArgumentCaptor.capture(), limitArgumentCaptor.capture());

        assertThat(startArgumentCaptor.getValue()).isEqualTo(expectedStart);

        verify(view).showError();
        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        verifyZeroInteractions(newsLoader, rxSchedulerUtil, view);
    }

    @Test
    public void loadingOfNewsFromApiWillSucceed() {
        List<News> expected = Observable.fromArray(dtos())
                                        .compose(new DtoToNewsMapper())
                                        .toList()
                                        .blockingGet();

        attachView();

        given(newsLoader.loadNews(anyInt(), anyInt())).willReturn(Single.just(expected));

        int expectedStart = 0;
        int expectedLimit = 10;

        classUnderTest.loadNews(expectedStart, expectedLimit);

        verify(view).showProgress();
        verify(view).hideProgress();

        ArgumentCaptor<Integer> startArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> limitArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(newsLoader).loadNews(startArgumentCaptor.capture(), limitArgumentCaptor.capture());

        assertThat(startArgumentCaptor.getValue()).isEqualTo(expectedStart);
        assertThat(limitArgumentCaptor.getValue()).isEqualTo(expectedLimit);

        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(view).showNews(listArgumentCaptor.capture());

        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        List<News> actual = listArgumentCaptor.getValue();

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected.get(i));
        }

        verifyNoMoreInteractions(newsLoader, rxSchedulerUtil, view);
    }
}