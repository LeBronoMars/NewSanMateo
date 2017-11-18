package sanmateo.com.profileapp.waterlevel;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import sanmateo.com.profileapp.user.login.LoginScope;
import sanmateo.com.profileapp.waterlevel.presenter.WaterLevelPresenterModule;
import sanmateo.com.profileapp.waterlevel.view.WaterLevelFragment;

@Subcomponent(modules = WaterLevelPresenterModule.class)
@LoginScope
public interface WaterLevelSubcomponent extends AndroidInjector<WaterLevelFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<WaterLevelFragment> {
    }
}
