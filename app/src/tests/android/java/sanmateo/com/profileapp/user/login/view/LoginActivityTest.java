package sanmateo.com.profileapp.user.login.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.factory.UserFactory;
import sanmateo.com.profileapp.framework.MockProfileApplication;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.local.loader.LocalUserLoader;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.BDDMockito.given;



/**
 * Created by rsbulanon on 11/11/2017.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
        new ActivityTestRule<>(LoginActivity.class, false, false);

    @Inject
    LocalUserLoader localUserLoader;

    @Before
    public void setUp() {
        Intents.init();
        ((MockProfileApplication) InstrumentationRegistry.getInstrumentation()
                                                         .getTargetContext()
                                                         .getApplicationContext()).getComponent()
                                                                                  .inject(this);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void loadLocalUserWillSucceed() {
        User expected = new UserDtoToUserMapper()
                            .apply(UserFactory.userDto())
                            .blockingGet();

        given(localUserLoader.loadLocalUser()).willReturn(Maybe.just(expected));

        showScreen();

        onView(withId(R.id.login_textview)).check(matches(withText(expected.firstName)));
    }

    private void showScreen() {
        activityTestRule.launchActivity(new Intent());
    }
}