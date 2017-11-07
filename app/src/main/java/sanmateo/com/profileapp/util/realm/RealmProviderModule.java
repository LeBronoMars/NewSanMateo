package sanmateo.com.profileapp.util.realm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@Module
public abstract class RealmProviderModule {

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RealmUtil<User> provideUserRealmUtil(Realm realm) {
        return new DefaultRealmUtil<>(realm);
    }
}
