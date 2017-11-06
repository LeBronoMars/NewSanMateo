package sanmateo.com.profileapp.user.login.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import sanmateo.com.profileapp.user.login.view.LoginView;

/**
 * Created by rsbulanon on 06/11/2017.
 */

public class DefaultLoginPresenter extends MvpBasePresenter<LoginView> implements LoginPresenter {

    @Inject
    public DefaultLoginPresenter() {
    }
}
