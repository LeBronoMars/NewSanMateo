package sanmateo.com.profileapp.splash.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface SplashView extends MvpView {

    void onRedirectToHome();

    void onRedirectToLogin();
}
