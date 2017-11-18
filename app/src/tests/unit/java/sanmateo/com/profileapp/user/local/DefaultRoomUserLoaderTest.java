package sanmateo.com.profileapp.user.local;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;


import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by rsbulanon on 17/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomUserLoaderTest {

    @Mock
    UserDao userDao;

    @InjectMocks
    DefaultRoomUserLoader classUnderTest;

    private User expected;

    @Before
    public void setUp() {
        expected = new UserDtoToUserMapper()
                            .apply(UserFactory.userDto())
                            .blockingGet();
    }

    @Test
    public void loadingOfCurrentLocalUserWillReturnEmpty() {
        given(userDao.findOne()).willReturn(Maybe.empty());

        classUnderTest.loadCurrentUser()
                      .test()
                      .assertNotComplete()
                      .assertError(NoQueryResultException.class);
    }

    @Test
    public void findByEmailWillSucceed() {
        given(userDao.findByEmail(anyString())).willReturn(Maybe.just(expected));

        classUnderTest.findByEmail(expected.email)
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(expected);
    }

    @Test
    public void findByEmailWillReturnEmpty() {
        given(userDao.findByEmail(anyString())).willReturn(Maybe.empty());

        classUnderTest.findByEmail(expected.email)
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertNever(expected);
    }

    @Test
    public void loadingOfCurrentLocalUserWillSucceed() {
        given(userDao.findOne()).willReturn(Maybe.just(expected));

        classUnderTest.loadCurrentUser()
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(expected);
    }
}