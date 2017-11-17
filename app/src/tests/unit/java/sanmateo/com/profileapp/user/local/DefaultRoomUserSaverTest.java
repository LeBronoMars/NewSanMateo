package sanmateo.com.profileapp.user.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import sanmateo.com.profileapp.database.AppDatabase;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.user.UserFactory.userDto;

/**
 * Created by rsbulanon on 17/11/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DefaultRoomUserSaverTest {

    UserDao userDao;

    DefaultRoomUserSaver classUnderTest;

    @Before
    public void setUp() {
        Context context = RuntimeEnvironment.application;

        AppDatabase appDatabase =
            Room.databaseBuilder(context, AppDatabase.class, "test-database")
                .allowMainThreadQueries()
                .build();

        userDao = appDatabase.userDao();

        classUnderTest = new DefaultRoomUserSaver(userDao);
    }

    @Test
    public void savingOfUserToLocalWillFail() {
        User expected = new UserDtoToUserMapper()
                            .apply(userDto())
                            .blockingGet();

        classUnderTest.saveUser(expected)
                      .test()
                      .assertNoErrors()
                      .assertComplete();

        User actual = userDao.findOne().blockingGet();

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}