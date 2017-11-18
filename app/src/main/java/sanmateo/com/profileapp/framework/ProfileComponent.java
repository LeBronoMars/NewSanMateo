package sanmateo.com.profileapp.framework;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import sanmateo.com.profileapp.api.RemoteServiceModule;
import sanmateo.com.profileapp.database.DatabaseModule;
import sanmateo.com.profileapp.splash.SplashModule;
import sanmateo.com.profileapp.user.UserModule;
import sanmateo.com.profileapp.util.rx.RxSchedulerModule;
import sanmateo.com.profileapp.waterlevel.WaterLevelModule;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Component(modules = {
                         AndroidInjectionModule.class,
                         AppModule.class,
                         DatabaseModule.class,
                         RemoteServiceModule.class,
                         RxSchedulerModule.class,
                         SplashModule.class,
                         UserModule.class,
                         WaterLevelModule.class
    })
@Singleton
public interface ProfileComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ProfileApplication application);
        ProfileComponent build();
    }

    void inject(ProfileApplication profileApplication);
}
