package sanmateo.com.profileapp.news.usecase.remote.mapper;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.factory.NewsFactory;
import sanmateo.com.profileapp.news.usecase.News;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rsbulanon on 02/12/2017.
 */
public class DtoToNewsMapperTest {

    @Test
    public void apply() {
        NewsDto expected = NewsFactory.dto();

        TestObserver<News> testObserver = new TestObserver<>();

        Observable.just(expected)
                  .compose(new DtoToNewsMapper())
                  .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isGreaterThan(0);

        News actual = testObserver.values().get(0);

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}