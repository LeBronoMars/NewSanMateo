package sanmateo.com.profileapp.splash;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import sanmateo.com.profileapp.splash.view.SplashActivity;
import sanmateo.com.profileapp.user.login.LoginSubComponent;
import sanmateo.com.profileapp.user.login.model.LoginModelModule;
import sanmateo.com.profileapp.user.login.view.LoginActivity;

@Module(subcomponents = {SplashSubcomponent.class}
    )
public abstract class SplashModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
        bindSplashActivitySubcomponent(SplashSubcomponent.Builder builder);
}
