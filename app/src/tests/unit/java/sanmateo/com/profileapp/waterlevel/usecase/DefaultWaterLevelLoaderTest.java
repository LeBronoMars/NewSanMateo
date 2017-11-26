package sanmateo.com.profileapp.waterlevel.usecase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelSaver;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteLoader;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by rsbulanon on 18/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWaterLevelLoaderTest {

    @Mock
    RoomWaterLevelLoader roomWaterLevelLoader;

    @Mock
    RoomWaterLevelSaver roomWaterLevelSaver;

    @Mock
    WaterLevelRemoteLoader waterLevelRemoteLoader;

    @InjectMocks
    private DefaultWaterLevelLoader classUnderTest;

    @Test
    public void loadingOfWaterLevelFromApiWillSucceed() {
        String expectedArea = "Area 1";

        List<WaterLevelDto> dtos = WaterLevelFactory.dtos();

        for (WaterLevelDto dto : dtos) {
            dto.area = expectedArea;
        }

        List<WaterLevel> expected = Observable.fromIterable(dtos)
                                              .compose(new DtoToWaterLevelMapper())
                                              .toList()
                                              .blockingGet();

        // assert that when API call is performed, will return this expected Dtos
        given(waterLevelRemoteLoader.waterLevels(anyString()))
            .willReturn(Observable.fromIterable(dtos));

        given(roomWaterLevelLoader.loadWaterLevel(anyString())).willReturn(Maybe.empty());

        TestObserver<List<WaterLevel>> testObserver = new TestObserver<>();

        classUnderTest.loadWaterLevels(expectedArea).subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isEqualTo(1);

        List<WaterLevel> actual = testObserver.values().get(0);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected.get(i));
        }
    }

    @Test
    public void loadDataFromLocalIfFetchedDataFromApiIsEmpty() {
        String expectedArea = "Area 1";

        List<WaterLevelDto> dtos = WaterLevelFactory.dtos();

        for (WaterLevelDto dto : dtos) {
            dto.area = expectedArea;
        }

        List<WaterLevel> expected = Observable.fromIterable(dtos)
                                              .compose(new DtoToWaterLevelMapper())
                                              .toList()
                                              .blockingGet();

        given(roomWaterLevelLoader.loadWaterLevel(anyString()))
            .willReturn(Maybe.just(expected));

        given(waterLevelRemoteLoader.waterLevels(anyString()))
            .willReturn(Observable.empty());

        TestObserver<List<WaterLevel>> testObserver = new TestObserver<>();

        classUnderTest.loadWaterLevels(expectedArea).subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isEqualTo(1);

        List<WaterLevel> actual = testObserver.values().get(0);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected.get(i));
        }
    }

    @Test
    public void willReturnEmptyListIfBothAPIAndLocalReturnEmpty() {
        given(roomWaterLevelLoader.loadWaterLevel(anyString()))
            .willReturn(Maybe.empty());

        given(waterLevelRemoteLoader.waterLevels(anyString()))
            .willReturn(Observable.empty());

        TestObserver<List<WaterLevel>> testObserver = new TestObserver<>();

        classUnderTest.loadWaterLevels("").subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isEqualTo(1);

        List<WaterLevel> actual = testObserver.values().get(0);

        assertThat(actual.size()).isZero();
    }
}