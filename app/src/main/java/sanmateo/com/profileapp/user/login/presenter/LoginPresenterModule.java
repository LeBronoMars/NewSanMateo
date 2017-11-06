package sanmateo.com.profileapp.user.login.presenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 06/11/2017.
 */

@Module
public abstract class LoginPresenterModule {

    @Binds
    abstract LoginPresenter bindsLoginPresenter(DefaultLoginPresenter defaultLoginPresenter);
}
