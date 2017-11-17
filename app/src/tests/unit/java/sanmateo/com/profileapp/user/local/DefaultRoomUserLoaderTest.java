package sanmateo.com.profileapp.user.local;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;


import static org.mockito.BDDMockito.given;

/**
 * Created by rsbulanon on 17/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomUserLoaderTest {

    @Mock
    UserDao userDao;

    @InjectMocks
    DefaultRoomUserLoader classUnderTest;

    @Test
    public void loadingOfCurrentLocalUserWillReturnEmpty() {
        given(userDao.findOne()).willReturn(Maybe.empty());

        TestObserver<User> testObserver = new TestObserver<>();

        classUnderTest.loadCurrentUser().subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void loadingOfCurrentLocalUserWillSucceed() {
        User expected = new UserDtoToUserMapper()
                            .apply(UserFactory.userDto())
                            .blockingGet();

        given(userDao.findOne()).willReturn(Maybe.just(expected));

        classUnderTest.loadCurrentUser()
                      .test()
                      .assertComplete()
                      .assertNoErrors()
                      .assertValue(expected);
    }
}