package sanmateo.com.profileapp.news;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import sanmateo.com.profileapp.news.presenter.NewsPresenterModule;
import sanmateo.com.profileapp.news.view.NewsFragment;
import sanmateo.com.profileapp.waterlevel.WaterLevelScope;

@Subcomponent(modules = NewsPresenterModule.class)
@NewsScope
public interface NewsSubcomponent extends AndroidInjector<NewsFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NewsFragment> {
    }
}
