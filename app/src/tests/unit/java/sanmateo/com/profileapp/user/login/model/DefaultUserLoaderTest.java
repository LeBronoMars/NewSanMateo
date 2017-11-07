package sanmateo.com.profileapp.user.login.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.user.UserDto;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.user.login.model.remote.LoginRemoteAuthenticator;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserLoaderTest {

    private DefaultUserLoader classUnderTest;

    @Mock
    private LoginRemoteAuthenticator loginRemoteAuthenticator;

    @Before
    public void setUp() {
        classUnderTest = new DefaultUserLoader(loginRemoteAuthenticator);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void loginSuccessful() {
        UserDto expectedResponse = UserFactory.userDto();

        // mock API behaviour that whenever loginRemoteAuthenticator#login with any string value
        // pass on it's parameter will succeed and will return the expectedResponse given above.
        given(loginRemoteAuthenticator.login(anyString(), anyString()))
            .willReturn(Single.just(expectedResponse));

        /**
         * Mimic the actual login api call
         *
         * After a successful login api request. We're assuming that {@link UserDto} will be
         * converted to {@link User}
         * */
        TestObserver<User> testObserver = new TestObserver<>();

        classUnderTest.login("", "").subscribe(testObserver);

        // assert that our request has been completed and we dont have any errors encountered.
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        // assert that expectedResponse is completely equal to the actualResponse
        User actualResponse = testObserver.values().get(0);
        assertThat(actualResponse).isEqualToComparingFieldByField(expectedResponse);
    }

}