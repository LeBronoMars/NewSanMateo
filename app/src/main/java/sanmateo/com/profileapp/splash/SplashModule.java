package sanmateo.com.profileapp.splash;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import sanmateo.com.profileapp.splash.usecase.SplashUseCaseModule;
import sanmateo.com.profileapp.splash.view.SplashActivity;

@Module(includes = SplashUseCaseModule.class,
    subcomponents = {SplashSubcomponent.class})
public abstract class SplashModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
        bindSplashActivitySubcomponent(SplashSubcomponent.Builder builder);
}
