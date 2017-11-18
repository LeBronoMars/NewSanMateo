package sanmateo.com.profileapp.waterlevel.local;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.util.RoomTestRule;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rsbulanon on 18/11/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DefaultRoomWaterLevelLoaderTest {

    @Rule
    public RoomTestRule roomTestRule = new RoomTestRule();

    private DefaultRoomWaterLevelLoader classUnderTest;

    private WaterLevelDao waterLevelDao;

    @Before
    public void setUp() {
        waterLevelDao = roomTestRule.getDatabase().waterLevelDao();

        classUnderTest = new DefaultRoomWaterLevelLoader(waterLevelDao);
    }

    @Test
    public void loadingOfWaterLevelFromLocalWillSucceed() {
        String expectedArea = "Area 1";

        ArrayList<WaterLevelDto> waterLevelDtos = WaterLevelFactory.dtos();

        for (WaterLevelDto dto : waterLevelDtos) {
            dto.area = expectedArea;
        }

        List<WaterLevel> expected = Observable.fromIterable(waterLevelDtos)
                                              .flatMapSingle(new DtoToWaterLevelMapper())
                                              .toSortedList((w1, w2)
                                                                -> w2.createdAt
                                                                       .compareTo(w1.createdAt))
                                              .blockingGet();

        for (WaterLevel waterLevel : expected) {
            waterLevelDao.insert(waterLevel);
        }

        List<WaterLevel> actual = classUnderTest.loadWaterLevel(expectedArea)
                                                .subscribeOn(Schedulers.io())
                                                .blockingGet();

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByField(expected.get(i ));
        }
    }
}