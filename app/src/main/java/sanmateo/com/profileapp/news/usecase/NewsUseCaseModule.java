package sanmateo.com.profileapp.news.usecase;

import dagger.Binds;
import dagger.Module;
import sanmateo.com.profileapp.news.local.RoomNewsModule;
import sanmateo.com.profileapp.news.usecase.remote.NewsRemoteModule;

/**
 * Created by rsbulanon on 28/11/2017.
 */
@Module(includes = {
                       RoomNewsModule.class,
                       NewsRemoteModule.class
})
public interface NewsUseCaseModule {

    @Binds
    NewsLoader bindNewsLoader(DefaultNewsLoader defaultNewsLoader);

}
