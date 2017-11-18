package sanmateo.com.profileapp.util;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.junit.rules.ExternalResource;
import org.robolectric.RuntimeEnvironment;

import sanmateo.com.profileapp.database.AppDatabase;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class RoomTestRule extends ExternalResource {

    private AppDatabase appDatabase;

    @Override
    protected void before() throws Throwable {
        super.before();

        Context context = RuntimeEnvironment.application;

        appDatabase =
            Room.databaseBuilder(context, AppDatabase.class, "test-database")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }
}
