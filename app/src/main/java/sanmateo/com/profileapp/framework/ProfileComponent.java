package sanmateo.com.profileapp.framework;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import sanmateo.com.profileapp.api.RemoteServiceModule;
import sanmateo.com.profileapp.user.UserModule;
import sanmateo.com.profileapp.util.realm.RealmProviderModule;
import sanmateo.com.profileapp.util.rx.RxSchedulerModule;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Component(modules = {
                         AndroidInjectionModule.class,
                         AppModule.class,
                         RealmProviderModule.class,
                         RemoteServiceModule.class,
                         RxSchedulerModule.class,
                         UserModule.class
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
