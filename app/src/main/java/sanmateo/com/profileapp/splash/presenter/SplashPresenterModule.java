package sanmateo.com.profileapp.splash.presenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 18/11/2017.
 */

@Module
public abstract class SplashPresenterModule {

    @Binds
    abstract SplashPresenter bindsSplashPresenter(DefaultSplashPresenter defaultSplashPresenter);
}
