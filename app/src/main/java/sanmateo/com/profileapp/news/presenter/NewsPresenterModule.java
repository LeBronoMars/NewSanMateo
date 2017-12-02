package sanmateo.com.profileapp.news.presenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 02/12/2017.
 */
@Module
public interface NewsPresenterModule {

    @Binds
    NewsPresenter bindNewsPresenter(DefaultNewsPresenter defaultNewsPresenter);
}
