package sanmateo.com.profileapp.user.login.model.local.loader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.user.local.RoomUserLoader;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by rsbulanon on 17/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultLocalUserLoaderTest {

    @Mock
    RoomUserLoader roomUserLoader;

    @InjectMocks
    private DefaultLocalUserLoader classUnderTest;

    @Test
    public void loadOfCurrentUserWillReturnEmpty() {
        given(roomUserLoader.loadCurrentUser()).willReturn(Maybe.empty());

        TestObserver<User> testObserver = new TestObserver<>();

        classUnderTest.loadLocalUser().subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void loadingOfCurrentLocalUserWillSucceed() {
        User expected = new UserDtoToUserMapper()
                            .apply(UserFactory.userDto())
                            .blockingGet();

        given(roomUserLoader.loadCurrentUser()).willReturn(Maybe.just(expected));

        TestObserver<User> testObserver = new TestObserver<>();

        classUnderTest.loadLocalUser().subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isEqualTo(1);

        User actual = testObserver.values().get(0);

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

}