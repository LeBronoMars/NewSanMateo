package sanmateo.com.profileapp.user.login.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 06/11/2017.
 */

public interface LoginView extends MvpView {

    String getEmail();

    String getPassword();

    void hideProgress();

    void showLoginFailed();

    void showProgress();

    void showLoginSuccess();
}
