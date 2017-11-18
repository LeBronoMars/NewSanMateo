package sanmateo.com.profileapp.splash;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.splash.presenter.DefaultSplashPresenter;
import sanmateo.com.profileapp.splash.presenter.SplashPresenter;

/**
 * Created by rsbulanon on 18/11/2017.
 */

@Module
public abstract class SplashModule {

    @Binds
    abstract SplashPresenter bindsSplashPresenter(DefaultSplashPresenter defaultSplashPresenter);
}
