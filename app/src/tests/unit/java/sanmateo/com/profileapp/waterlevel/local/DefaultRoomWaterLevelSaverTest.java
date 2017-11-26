package sanmateo.com.profileapp.waterlevel.local;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.Observable;
import sanmateo.com.profileapp.util.RoomTestRule;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.WaterLevelFactory.dtos;

/**
 * Created by rsbulanon on 18/11/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DefaultRoomWaterLevelSaverTest {

    @Rule
    public RoomTestRule roomTestRule = new RoomTestRule();

    private DefaultRoomWaterLevelSaver classUnderTest;

    private WaterLevelDao waterLevelDao;


    @Before
    public void setUp() {
        waterLevelDao = roomTestRule.getDatabase().waterLevelDao();

        classUnderTest = new DefaultRoomWaterLevelSaver(waterLevelDao);
    }

    @Test
    public void savingOfWaterLevelToLocalWillSucceed() {
        waterLevelDao.count()
                     .test()
                     .assertValue(0l);

        List<WaterLevel> expected = Observable.fromIterable(dtos())
                                              .compose(new DtoToWaterLevelMapper())
                                              .toSortedList((w1, w2)
                                                                -> w2.createdAt
                                                                       .compareTo(w1.createdAt))
                                              .blockingGet();

        classUnderTest.saveWaterLevel(expected)
                      .test()
                      .assertComplete()
                      .assertNoErrors();

        waterLevelDao.count()
                     .test()
                     .assertValue(Long.valueOf(expected.size()));
    }
}