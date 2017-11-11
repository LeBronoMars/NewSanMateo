package sanmateo.com.profileapp.framework;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import sanmateo.com.profileapp.util.realm.RealmProviderModule;
import sanmateo.com.profileapp.util.rx.MockRxSchedulerModule;

/**
 * Created by rsbulanon on 11/11/2017.
 */


@Component(
    modules = {
                  AndroidInjectionModule.class,
                  MockAppModule.class,
                  MockRxSchedulerModule.class,
                  RealmProviderModule.class
    })
@Singleton
public interface MockProfileComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        MockProfileComponent.Builder application(MockProfileApplication application);
        MockProfileComponent build();
    }
}
