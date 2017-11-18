package sanmateo.com.profileapp.waterlevel.usecase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelSaver;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteLoader;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;


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
    public void loadingOfWaterLevelWillSucceed() {
        String expectedArea = "Area 1";

        List<WaterLevelDto> dtos = WaterLevelFactory.dtos();

        for (WaterLevelDto dto : dtos) {
            dto.area = expectedArea;
        }

        List<WaterLevel> expected = Observable.fromIterable(dtos)
                                              .flatMapSingle(new DtoToWaterLevelMapper())
                                              .toSortedList((w1, w2)
                                                                -> w2.createdAt
                                                                       .compareTo(w1.createdAt))
                                              .blockingGet();

        given(roomWaterLevelLoader.loadWaterLevel(anyString())).willReturn(Maybe.just(expected));

        given(waterLevelRemoteLoader.waterLevels(anyString())).willReturn(Single.just(dtos));

        classUnderTest.loadWaterLevels(expectedArea)
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(expected);
    }
}