package sanmateo.com.profileapp.waterlevel.presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.util.TestableRxSchedulerUtil;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;
import sanmateo.com.profileapp.waterlevel.view.WaterLevelView;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by rsbulanon on 19/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWaterLevelPresenterTest {

    @Spy
    private TestableRxSchedulerUtil rxSchedulerUtil;

    @Mock
    private WaterLevelLoader waterLevelLoader;

    @Mock
    private WaterLevelView view;

    @InjectMocks
    private DefaultWaterLevelPresenter classUnderTest;

    @Test
    public void attachView() {
        classUnderTest.attachView(view);
        assertThat(view).isSameAs(classUnderTest.getView());
    }

    @Test
    public void loadingOfWaterLevelsWillSucceed() {
        String expectedArea = "Area 1";

        List<WaterLevelDto> dtos = WaterLevelFactory.dtos(expectedArea);

        List<WaterLevel> expected = Observable.fromIterable(dtos)
                                              .compose(new DtoToWaterLevelMapper())
                                              .toList()
                                              .blockingGet();

        given(waterLevelLoader.loadWaterLevels(anyString())).willReturn(Single.just(expected));

        attachView();

        classUnderTest.loadWaterLevel(expectedArea);

        verify(view).showProgress(expectedArea);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(waterLevelLoader).loadWaterLevels(expectedArea);

        verify(view).showWaterLevels(stringArgumentCaptor.capture(),
                                     listArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expectedArea);
        assertThat(listArgumentCaptor.getValue().size()).isEqualTo(expected.size());

        verify(view).hideProgress(expectedArea);

        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        verifyNoMoreInteractions(rxSchedulerUtil, waterLevelLoader, view);
    }

    @Test
    public void loadingOfWaterLevelsWillFail() {
        given(waterLevelLoader.loadWaterLevels(anyString()))
            .willReturn(Single.error(new Throwable()));

        attachView();

        classUnderTest.loadWaterLevel("");

        verify(view).showProgress("");

        verify(waterLevelLoader).loadWaterLevels("");

        verify(view).hideProgress("");

        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        verify(view).showError("");

        verifyNoMoreInteractions(rxSchedulerUtil, waterLevelLoader, view);
    }
}