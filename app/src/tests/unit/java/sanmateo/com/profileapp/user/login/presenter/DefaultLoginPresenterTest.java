package sanmateo.com.profileapp.user.login.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.api.user.InvalidAccountException;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.UserLoader;
import sanmateo.com.profileapp.user.login.model.local.loader.LocalUserLoader;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;
import sanmateo.com.profileapp.user.login.view.LoginView;
import sanmateo.com.profileapp.util.TestableRxSchedulerUtil;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static sanmateo.com.profileapp.factory.user.UserFactory.userDto;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultLoginPresenterTest {

    @Mock
    LoginView view;

    @Mock
    LocalUserLoader localUserLoader;

    @Spy
    TestableRxSchedulerUtil rxSchedulerUtil;

    @Mock
    UserLoader userLoader;

    private DefaultLoginPresenter classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new DefaultLoginPresenter(localUserLoader, rxSchedulerUtil, userLoader);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void attachView() {
        classUnderTest.attachView(view);
        assertThat(classUnderTest.view).isSameAs(view);
    }

    @Test
    public void detachView() {
        classUnderTest.disposable = Completable.complete()
                                               .delay(2, TimeUnit.SECONDS)
                                               .subscribe();
        assertThat(classUnderTest.disposable.isDisposed()).isFalse();
        classUnderTest.detachView(true);
        assertThat(classUnderTest.disposable.isDisposed()).isTrue();
    }

    @Test
    public void loadingOfLocalUserWillSucceed() {
        attachView();

        User expected = new UserDtoToUserMapper()
                            .apply(userDto())
                            .blockingGet();

        // when localUserLoader#loadLocalUser is invoke return our expected User object
        given(localUserLoader.loadLocalUser()).willReturn(Maybe.just(expected));

        // trigger checking and loading of existing user
        classUnderTest.checkForLocalUser();

        // This is to capture the User produced when loading of local succeeds
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        // capture produced User object received by this LoginView#loadLocalUser
        verify(view).loadLocalUser(userArgumentCaptor.capture());

        User actual = userArgumentCaptor.getValue();

        assertThat(actual).isNotNull();

        // verify that captured User object is equal to our expected User object
        assertThat(actual.id).isEqualTo(expected.id);
        assertThat(actual.firstName).isEqualTo(expected.firstName);
        assertThat(actual.lastName).isEqualTo(expected.lastName);
        assertThat(actual.email).isEqualTo(expected.email);
        assertThat(actual.picUrl).isEqualTo(expected.picUrl);

        // verify that our request runs on the background thread and broadcasted the
        // result to the main thread after the request.
        verify(rxSchedulerUtil).mayBeAsyncSchedulerTransformer();

        // verify that LoginView#noLocalUser callback was never called.
        verify(view, never()).noLocalUser();

        // since we are done with our Login Request. This is to verify that we dont have
        // any interactions with any objects related to login.
        verifyNoMoreInteractions(rxSchedulerUtil, userLoader, view);
    }

    @Test
    public void loadingOfLocalUserWillFail() {
        attachView();

        given(localUserLoader.loadLocalUser()).willReturn(Maybe.empty());

        classUnderTest.checkForLocalUser();

        // verify that this callback was never called, since we have not loaded any user from realm.
        verify(view, never()).loadLocalUser(any(User.class));

        // verify that this callback was called instead.
        verify(view).noLocalUser();

        verify(rxSchedulerUtil).mayBeAsyncSchedulerTransformer();

        // since we are done with our Login Request. This is to verify that we dont have
        // any interactions with any objects related to login.
        verifyNoMoreInteractions(rxSchedulerUtil, userLoader, view);
    }

    @Test
    public void loginSuccessful() {
        // attach our view
        attachView();

        // set expected User response. We use the Mapper to convert the UserDto to User
        User expected = new UserDtoToUserMapper()
                            .apply(userDto())
                            .blockingGet();

        String expectedEmail = "ned@fanders.com";
        String expectedPassword = "P@ssw0rd";

        // when LoginView#getEmail is called, it will return ned@flanders.com
        given(view.getEmail()).willReturn(expectedEmail);

        // when LoginView#getPassword is called, it will return P@ssw0rd
        given(view.getPassword()).willReturn(expectedPassword);

        // given when userLoader#login with any string value passed on its parameter, it will
        // return our expected response set above.
        given(userLoader.login(anyString(), anyString())).willReturn(Single.just(expected));

        // perform actual login call
        classUnderTest.login();

        // verify that show progress was called.
        verify(view).showProgress();

        // verify that UserLoader#login was called only once and to verify if the parameters received
        // by this method is equal to our given expected email & password using ArgumentCaptor.
        ArgumentCaptor<String> argumentCaptorEmail = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentCaptorPassword = ArgumentCaptor.forClass(String.class);

        verify(userLoader, times(1)).login(argumentCaptorEmail.capture(),
                                           argumentCaptorPassword.capture());

        assertThat(argumentCaptorEmail.getValue()).isEqualTo(expectedEmail);
        assertThat(argumentCaptorPassword.getValue()).isEqualTo(expectedPassword);

        verify(view).getEmail();

        verify(view).getPassword();

        // verify that hide progress was called.
        verify(view).hideProgress();

        // verify that login success was called.
        verify(view).showLoginSuccess();

        // verify that we use the single scheduler
        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        // since we are done with our Login Request. This is to verify that we dont have
        // any interactions with any objects related to login.
        verifyNoMoreInteractions(rxSchedulerUtil, userLoader, view);
    }

    @Test
    public void loginFailed() {
        // attach our view
        attachView();

        String expectedEmail = "ned@fanders.com";
        String expectedPassword = "P@ssw0rd";

        // when LoginView#getEmail is called, it will return ned@flanders.com
        given(view.getEmail()).willReturn(expectedEmail);

        // when LoginView#getPassword is called, it will return P@ssw0rd
        given(view.getPassword()).willReturn(expectedPassword);

        // given when userLoader#login with any string value passed on its parameter, it will
        // return an InvalidAccountException
        given(userLoader.login(anyString(), anyString()))
            .willReturn(Single.error(InvalidAccountException::new));

        // perform actual login call
        classUnderTest.login();

        // verify that show progress was called.
        verify(view).showProgress();

        // verify that UserLoader#login was called only once and to verify if the parameters received
        // by this method is equal to our given expected email & password using ArgumentCaptor.
        ArgumentCaptor<String> argumentCaptorEmail = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentCaptorPassword = ArgumentCaptor.forClass(String.class);

        verify(userLoader, times(1)).login(argumentCaptorEmail.capture(),
                                           argumentCaptorPassword.capture());

        assertThat(argumentCaptorEmail.getValue()).isEqualTo(expectedEmail);
        assertThat(argumentCaptorPassword.getValue()).isEqualTo(expectedPassword);

        verify(view).getEmail();

        verify(view).getPassword();

        // verify that hide progress was called.
        verify(view).hideProgress();

        // verify that login failed was called.
        verify(view).showLoginFailed();

        // verify that we use the single scheduler
        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        // since we are done with our Login Request. This is to verify that we dont have
        // any interactions with any objects related to login.
        verifyNoMoreInteractions(rxSchedulerUtil, userLoader, view);

        // verify that we don't performed any interaction with realmUtil object
         verifyZeroInteractions(localUserLoader);
    }
}