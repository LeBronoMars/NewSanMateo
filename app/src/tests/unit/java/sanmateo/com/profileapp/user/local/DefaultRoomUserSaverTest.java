package sanmateo.com.profileapp.user.local;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;
import sanmateo.com.profileapp.util.RoomTestRule;


import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.user.UserFactory.userDto;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DefaultRoomUserSaverTest {

    @Rule
    public RoomTestRule roomTestRule = new RoomTestRule();

    DefaultRoomUserSaver classUnderTest;

    UserDao userDao;

    @Before
    public void setUp() {
        userDao = roomTestRule.getDatabase().userDao();

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