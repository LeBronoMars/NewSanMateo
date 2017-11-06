package sanmateo.com.profileapp.user.login;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import sanmateo.com.profileapp.user.login.presenter.LoginPresenterModule;
import sanmateo.com.profileapp.user.login.view.LoginActivity;

/**
 * Created by rsbulanon on 06/11/2017.
 */

@Subcomponent(modules = LoginPresenterModule.class)
@LoginScope
public interface LoginSubComponent extends AndroidInjector<LoginActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<LoginActivity> {
    }
}
