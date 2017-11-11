package sanmateo.com.profileapp.framework;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rsbulanon on 11/11/2017.
 */

@Module
public class MockAppModule {

    @Provides
    Context provideContext(ProfileApplication application) {
        return application.getApplicationContext();
    }
}
