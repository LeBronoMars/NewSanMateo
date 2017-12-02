package sanmateo.com.profileapp.news.local.loader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.news.local.NewsDao;
import sanmateo.com.profileapp.news.usecase.News;
import sanmateo.com.profileapp.news.usecase.remote.mapper.DtoToNewsMapper;
import sanmateo.com.profileapp.util.RoomTestRule;


import static java.util.Calendar.JANUARY;
import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.NewsFactory.dtos;
import static sanmateo.com.profileapp.util.TimeUtils.date;
import static sanmateo.com.profileapp.util.TimeUtils.toDate;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23)
public class DefaultRoomNewsLoaderTest {

    @Rule
    public RoomTestRule rule = new RoomTestRule();

    private DefaultRoomNewsLoader classUnderTest;

    private NewsDao newsDao;

    @Before
    public void setUp() {
        newsDao = rule.getDatabase().newsDao();

        classUnderTest = new DefaultRoomNewsLoader(newsDao);
    }

    @Test
    public void loadingOfCachedNewsCountWillSucceed() {
        classUnderTest.count()
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(0l);
    }

    @Test
    public void loadingOfCachedNewsWillSucceed() {
        NewsDto[] newsDtos = dtos();

        for (int i = 0; i < newsDtos.length; i++) {
            //this is to ensure that all ids are unique
            newsDtos[i].id = i;
            newsDtos[i].updatedAt = toDate(date(JANUARY, (i + 1), 2017));
        }

        List<News> expected = Observable.fromArray(newsDtos)
                                    .compose(new DtoToNewsMapper())
                                    .toSortedList((n1, n2) -> n2.updatedAt.compareTo(n1.updatedAt))
                                    .blockingGet();

        newsDao.insertAll(expected);

        List<News> actual = classUnderTest.loadNews()
                                          .subscribeOn(Schedulers.io())
                                          .blockingGet();

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i))
                .isEqualToComparingFieldByField(expected.get(i));
        }
    }
}