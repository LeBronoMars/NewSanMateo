package sanmateo.com.profileapp.user.login.presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import io.reactivex.Completable;
import io.reactivex.Single;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.UserLoader;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;
import sanmateo.com.profileapp.user.login.view.LoginView;
import sanmateo.com.profileapp.util.TestableRxSchedulerUtil;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static sanmateo.com.profileapp.factory.UserFactory.userDto;

/**
 * Created by rsbulanon on 07/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultLoginPresenterTest {

    @Mock
    LoginView view;

    @Spy
    TestableRxSchedulerUtil rxSchedulerUtil;

    @Mock
    UserLoader userLoader;

    @InjectMocks
    private DefaultLoginPresenter classUnderTest;


    @Test
    public void attachView() {
        classUnderTest.attachView(view);
        assertThat(classUnderTest.view).isSameAs(view);
    }

    @Test
    public void detachView() {
        classUnderTest.compositeDisposable.add(Completable.complete()
                                               .delay(2, TimeUnit.SECONDS)
                                               .subscribe());
        assertThat(classUnderTest.compositeDisposable.isDisposed()).isFalse();
        classUnderTest.detachView(true);
        assertThat(classUnderTest.compositeDisposable.isDisposed()).isTrue();
    }

    @Test
    public void loadingOfUserWillSucceed() {
        attachView();

        User expected = new UserDtoToUserMapper()
                            .apply(userDto())
                            .blockingGet();

        String expectedEmail = expected.email;
        String expectedPassword = "Passw0rd";

        given(userLoader.login(anyString(), anyString())).willReturn(Single.just(expected));

        given(view.getEmail()).willReturn(expectedEmail);

        given(view.getPassword()).willReturn(expectedPassword);

        classUnderTest.login();

        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(view).getEmail();
        verify(view).getPassword();

        verify(userLoader).login(emailArgumentCaptor.capture(), passwordArgumentCaptor.capture());

        assertThat(emailArgumentCaptor.getValue()).isEqualTo(expectedEmail);
        assertThat(passwordArgumentCaptor.getValue()).isEqualTo(expectedPassword);

        // verify that our request runs on the background thread and broadcasted the
        // result to the main thread after the request.
        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        verify(view).showProgress();
        verify(view).showLoginSuccess();
        verify(view).hideProgress();

        // since we are done with our Login Request. This is to verify that we dont have
        // any interactions with any objects related to login.
        verifyNoMoreInteractions(rxSchedulerUtil, userLoader, view);
    }

    @Test
    public void loadingOfUserWillFail() {
        attachView();

        String expectedEmail = "nedflanders";
        String expectedPassword = "Passw0rd";

        given(userLoader.login(anyString(), anyString()))
            .willReturn(Single.error(LoginException::new));

        given(view.getEmail()).willReturn(expectedEmail);

        given(view.getPassword()).willReturn(expectedPassword);

        classUnderTest.login();

        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(view).getEmail();
        verify(view).getPassword();

        verify(userLoader).login(emailArgumentCaptor.capture(), passwordArgumentCaptor.capture());

        assertThat(emailArgumentCaptor.getValue()).isEqualTo(expectedEmail);
        assertThat(passwordArgumentCaptor.getValue()).isEqualTo(expectedPassword);

        // verify that our request runs on the background thread and broadcasted the
        // result to the main thread after the request.
        verify(rxSchedulerUtil).singleAsyncSchedulerTransformer();

        verify(view).showProgress();
        verify(view).showLoginFailed();
        verify(view).hideProgress();

        // since we are done with our Login Request. This is to verify that we dont have
        // any interactions with any objects related to login.
        verifyNoMoreInteractions(rxSchedulerUtil, userLoader, view);
    }
}