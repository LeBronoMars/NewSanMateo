package sanmateo.com.profileapp.splash;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import sanmateo.com.profileapp.splash.presenter.SplashPresenterModule;
import sanmateo.com.profileapp.splash.view.SplashActivity;
import sanmateo.com.profileapp.user.login.LoginScope;
import sanmateo.com.profileapp.user.login.presenter.LoginPresenterModule;
import sanmateo.com.profileapp.user.login.view.LoginActivity;

/**
 * Created by rsbulanon on 06/11/2017.
 */

@Subcomponent(modules = SplashPresenterModule.class)
@SplashScope
public interface SplashSubcomponent extends AndroidInjector<SplashActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SplashActivity> {
    }
}
