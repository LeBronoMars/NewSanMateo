package sanmateo.com.profileapp.user.login.model.remote.mapper;

import org.junit.Test;

import sanmateo.com.profileapp.api.user.UserDto;
import sanmateo.com.profileapp.user.login.model.local.User;


import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.user.UserFactory.userDto;

/**
 * Created by rsbulanon on 31/10/2017.
 */
public class LoginRemoteMapperTest {

    @Test
    public void apply() {
        UserDto expected = userDto();

        User actual = new LoginRemoteMapper().apply(expected).blockingGet();

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}