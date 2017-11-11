package sanmateo.com.profileapp.util.realm;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module
public class RealmProviderModule {

    @Provides
    @Singleton
    Realm provideRealm(Context context) {
        Realm.init(context);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RealmUtil<User> provideUserRealm(Realm realm) {
        return new DefaultRealmUtil<>(realm);
    }
}
