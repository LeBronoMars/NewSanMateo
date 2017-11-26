package sanmateo.com.profileapp.splash.usecase;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 26/11/2017.
 */

@Module
public abstract class SplashUseCaseModule {

    @Binds
    abstract SplashLoader bindSplashLoader(DefaultSplashLoader splashLoader);
}
