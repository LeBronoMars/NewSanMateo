package sanmateo.com.profileapp.database;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sanmateo.com.profileapp.framework.ProfileApplication;
import sanmateo.com.profileapp.user.local.UserDao;

/**
 * Created by rsbulanon on 17/11/2017.
 */

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    static AppDatabase provideDatabase(ProfileApplication profileApplication) {
        return Room.databaseBuilder(profileApplication,
                                    AppDatabase.class, "database.sql").build() ;
    }

    @Provides
    @Singleton
    static UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }
}
