package sanmateo.com.profileapp.user.login.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import io.reactivex.Single;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.framework.MockProfileApplication;
import sanmateo.com.profileapp.user.login.model.UserLoader;
import sanmateo.com.profileapp.user.login.model.remote.LoginRemoteAuthenticator;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.matches;



/**
 * Created by rsbulanon on 11/11/2017.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
        new ActivityTestRule<>(LoginActivity.class, false, false);

    @Inject
    UserLoader userLoader;

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
    public void loginButtonIsDisabledIfUsernameAndPasswordFieldsAreEmpty() {
        showScreen();

        // assert that login button is disabled by default
        onView(withId(R.id.login_button)).check(matches(not(isEnabled())));

        onView(withId(R.id.login_edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.login_edit_text_password)).perform(typeText("password"));

        // assert that login button is now enabled
        onView(withId(R.id.login_button)).check(matches(isEnabled()));
    }

    @Test
    public void loginWillFail() {
        given(userLoader.login(anyString(), anyString()))
            .willReturn(Single.error(LoginException::new));

        showScreen();

        onView(withId(R.id.login_edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.login_edit_text_password)).perform(typeText("password"));

        onView(withId(R.id.login_button)).perform(click());

        onView(withText(R.string.login_message_failed)).check(matches(isDisplayed()));
    }

    private void showScreen() {
        activityTestRule.launchActivity(new Intent());
    }
}