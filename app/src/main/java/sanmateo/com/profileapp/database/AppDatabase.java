package sanmateo.com.profileapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import sanmateo.com.profileapp.user.local.UserDao;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */

@Database(
    entities = {
       User.class
    },
    exportSchema = false,
    version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
