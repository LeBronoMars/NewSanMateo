package sanmateo.com.profileapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import sanmateo.com.profileapp.news.local.NewsDao;
import sanmateo.com.profileapp.news.usecase.News;
import sanmateo.com.profileapp.user.local.UserDao;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.waterlevel.local.WaterLevelDao;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 17/11/2017.
 */

@Database(
    entities = {
       News.class,
       User.class,
       WaterLevel.class
    },
    exportSchema = false,
    version = 2
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    public abstract UserDao userDao();

    public abstract WaterLevelDao waterLevelDao();
}
