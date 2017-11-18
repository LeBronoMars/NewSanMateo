package sanmateo.com.profileapp.splash.presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.factory.user.UserFactory;
import sanmateo.com.profileapp.splash.view.SplashView;
import sanmateo.com.profileapp.user.local.NoQueryResultException;
import sanmateo.com.profileapp.user.local.RoomUserLoader;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.remote.mapper.UserDtoToUserMapper;
import sanmateo.com.profileapp.util.TestableRxSchedulerUtil;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by rsbulanon on 18/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultSplashPresenterTest {

    @Mock
    RoomUserLoader roomUserLoader;

    @Spy
    TestableRxSchedulerUtil rxSchedulerUtil;

    @Mock
    SplashView splashView;

    @InjectMocks
    DefaultSplashPresenter classUnderTest;

    @Test
    public void redirectsToLoginScreenIfThereIsNoStoredLocalUser() {
        classUnderTest.attachView(splashView);

        given(roomUserLoader.loadCurrentUser())
            .willReturn(Maybe.error(NoQueryResultException::new));

        classUnderTest.checkForExistingUser();

        verify(roomUserLoader, times(1)).loadCurrentUser();

        verify(rxSchedulerUtil).mayBeAsyncSchedulerTransformer();

        verify(splashView).onRedirectToLogin();

        verifyNoMoreInteractions(roomUserLoader, rxSchedulerUtil, splashView);
    }

    @Test
    public void redirectsToHomeSreenIfThereIsStoredLocalUser() {
        classUnderTest.attachView(splashView);

        User expected = new UserDtoToUserMapper()
                            .apply(UserFactory.userDto())
                            .blockingGet();

        given(roomUserLoader.loadCurrentUser()).willReturn(Maybe.just(expected));

        classUnderTest.checkForExistingUser();

        verify(roomUserLoader, times(1)).loadCurrentUser();

        verify(rxSchedulerUtil).mayBeAsyncSchedulerTransformer();

        verify(splashView).onRedirectToHome();

        verifyNoMoreInteractions(roomUserLoader, rxSchedulerUtil, splashView);
    }
}