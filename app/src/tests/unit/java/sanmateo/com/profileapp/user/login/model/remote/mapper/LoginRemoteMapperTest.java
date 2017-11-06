package sanmateo.com.profileapp.user.login.model.remote.mapper;

import org.junit.Test;

import io.reactivex.observers.TestObserver;
import sanmateo.com.profileapp.api.user.UserDto;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.user.login.model.User;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rsbulanon on 31/10/2017.
 */
public class LoginRemoteMapperTest {

    @Test
    public void apply() {
        TestObserver<User> testObserver = new TestObserver<>();

        UserDto expected = UserFactory.userDto();

        new LoginRemoteMapper()
                          .apply(expected)
                          .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        assertThat(testObserver.valueCount()).isEqualTo(1);

        User actual = testObserver.values().get(0);

        assertThat(actual).isNotNull();

        assertThat(actual.email).isEqualTo(expected.email);

        //assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private void print() {
        // do something
    }
}