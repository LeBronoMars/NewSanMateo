package sanmateo.com.profileapp.database;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sanmateo.com.profileapp.framework.ProfileApplication;
import sanmateo.com.profileapp.incident.usecase.local.IncidentDao;
import sanmateo.com.profileapp.news.local.NewsDao;
import sanmateo.com.profileapp.user.local.UserDao;
import sanmateo.com.profileapp.waterlevel.local.WaterLevelDao;

/**
 * Created by rsbulanon on 17/11/2017.
 */

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    static AppDatabase provideDatabase(ProfileApplication profileApplication) {
        return Room.databaseBuilder(profileApplication,
                                    AppDatabase.class, "database.sql")
                   .fallbackToDestructiveMigration()
                   .build();
    }

    @Provides
    @Singleton
    static IncidentDao provideIncidentDao(AppDatabase appDatabase) {
        return appDatabase.incidentDao();
    }

    @Provides
    @Singleton
    static NewsDao provideNewsDao(AppDatabase appDatabase) {
        return appDatabase.newsDao();
    }

    @Provides
    @Singleton
    static UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Provides
    @Singleton
    static WaterLevelDao provideWaterLevelDao(AppDatabase appDatabase) {
        return appDatabase.waterLevelDao();
    }
}
