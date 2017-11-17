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
public class DefaultRoomUserDeleterTest {

    UserDao userDao;

    DefaultRoomUserDeleter classUnderTest;

    @Before
    public void setUp() {
        Context context = RuntimeEnvironment.application;

        AppDatabase appDatabase =
            Room.databaseBuilder(context, AppDatabase.class, "test-database")
                .allowMainThreadQueries()
                .build();

        userDao = appDatabase.userDao();

        classUnderTest = new DefaultRoomUserDeleter(userDao);
    }

    @Test
    public void deleteUser() {
        User expected = new UserDtoToUserMapper()
                            .apply(userDto())
                            .blockingGet();
        // create record
        userDao.insert(expected);

        // query for the newly created record and must return the expected User
        User actual = userDao.findOne().blockingGet();
        assertThat(actual).isEqualToComparingFieldByField(expected);

        // delete the record
        classUnderTest.delete(expected)
                      .test()
                      .assertComplete()
                      .assertNoErrors();

        // assert that query doesn't return anything since we have already deleted it.
        userDao.findOne()
               .test()
               .assertComplete()
               .assertNoErrors();
    }
}