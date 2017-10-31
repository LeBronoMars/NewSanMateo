package sanmateo.com.profileapp.framework;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Component(modules = {
                         AndroidInjectionModule.class,
                         AppModule.class
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
