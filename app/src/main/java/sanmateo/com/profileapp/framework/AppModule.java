package sanmateo.com.profileapp.framework;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rsbulanon on 31/10/2017.
 */

@Module
public class AppModule {

    @Provides
    Context provideContext(ProfileApplication application) {
        return application.getApplicationContext();
    }
}
