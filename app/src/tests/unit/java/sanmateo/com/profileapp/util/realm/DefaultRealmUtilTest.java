package sanmateo.com.profileapp.util.realm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.realm.Realm;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultRealmUtilTest {

    @Mock
    Realm realm;

    private DefaultRealmUtil<User> classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new DefaultRealmUtil<>(realm);
    }

    @Test
    public void save() {
        given(realm)

        long count = classUnderTest.count().blockingGet();

        assertThat(count).isZero();

        User expected = new UserDtoToUserMapper()
                            .apply(UserFactory.userDto())
                            .blockingGet();

        classUnderTest.replaceInto(expected);

        long newCount = classUnderTest.count().blockingGet();

        assertThat(newCount).isNotZero();
    }
}