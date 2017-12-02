package sanmateo.com.profileapp.news;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import sanmateo.com.profileapp.news.usecase.NewsUseCaseModule;
import sanmateo.com.profileapp.news.view.NewsFragment;

/**
 * Created by rsbulanon on 26/11/2017.
 */
@Module(subcomponents = NewsSubcomponent.class,
    includes = NewsUseCaseModule.class)
public interface NewsModule {

    @Binds
    @IntoMap
    @FragmentKey(NewsFragment.class)
    AndroidInjector.Factory<? extends Fragment>
        bindNewsFragment(NewsSubcomponent.Builder builder);
}
