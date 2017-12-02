package sanmateo.com.profileapp.news.usecase.remote;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 28/11/2017.
 */

@Module
public interface NewsRemoteModule {

    @Binds
    NewsRemoteLoader bindsNewsRemoteLoader(
        DefaultNewsRemoteLoader defaultNewsRemoteLoader);
}
