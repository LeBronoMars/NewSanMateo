package sanmateo.com.profileapp.splash.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import sanmateo.com.profileapp.splash.view.SplashView;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface SplashPresenter extends MvpPresenter<SplashView> {

    void checkForExistingUser();
}
