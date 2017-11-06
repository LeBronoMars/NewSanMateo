package sanmateo.com.profileapp.user.login;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import sanmateo.com.profileapp.user.login.model.LoginModelModule;
import sanmateo.com.profileapp.user.login.view.LoginActivity;

/**
 * Created by rsbulanon on 06/11/2017.
 */
@Module(subcomponents = {LoginSubComponent.class},
    includes = LoginModelModule.class)
public abstract class LoginModule {

    @Binds
    @IntoMap
    @ActivityKey(LoginActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
        bindLoginActivityInjectorFactory(LoginSubComponent.Builder builder);
}
