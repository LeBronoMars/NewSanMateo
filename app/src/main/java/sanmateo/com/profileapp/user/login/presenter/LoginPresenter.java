package sanmateo.com.profileapp.user.login.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import sanmateo.com.profileapp.user.login.view.LoginView;

/**
 * Created by rsbulanon on 06/11/2017.
 */

public interface LoginPresenter extends MvpPresenter<LoginView> {

    void login();
}
