package sanmateo.com.profileapp.news.local.saver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.Observable;
import sanmateo.com.profileapp.news.local.NewsDao;
import sanmateo.com.profileapp.news.usecase.News;
import sanmateo.com.profileapp.news.usecase.remote.mapper.DtoToNewsMapper;
import sanmateo.com.profileapp.util.RoomTestRule;


import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.NewsFactory.dtos;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23)
public class DefaultRoomNewsSaverTest {

    @Rule
    public RoomTestRule rule = new RoomTestRule();

    private DefaultRoomNewsSaver classUnderTest;

    private NewsDao newsDao;

    @Before
    public void setUp() {
        newsDao = rule.getDatabase().newsDao();

        classUnderTest = new DefaultRoomNewsSaver(newsDao);
    }

    @Test
    public void savingOfNewsWillSucceed() {
        // assert that count is Zero before saving
        assertThat(newsDao.count().blockingGet()).isZero();

        List<News> expected = Observable.fromArray(dtos())
                                    .compose(new DtoToNewsMapper())
                                    .toList()
                                    .blockingGet();

        classUnderTest.saveNews(expected).blockingAwait();

        // assert that after saving our size is now is not equal to Zero.
        assertThat(newsDao.count().blockingGet()).isNotZero();
    }
}