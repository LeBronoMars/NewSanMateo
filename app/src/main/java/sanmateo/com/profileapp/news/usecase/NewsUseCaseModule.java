package sanmateo.com.profileapp.news.usecase;

import dagger.Module;
import sanmateo.com.profileapp.news.usecase.remote.NewsRemoteModule;

/**
 * Created by rsbulanon on 28/11/2017.
 */
@Module(includes = {
                       NewsRemoteModule.class
})
public interface NewsUseCaseModule {

}
