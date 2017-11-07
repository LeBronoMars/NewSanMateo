package sanmateo.com.profileapp.user.login.view;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import sanmateo.com.profileapp.user.login.presenter.LoginPresenter;

/**
 * Created by rsbulanon on 06/11/2017.
 */

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @Inject
    LoginPresenter presenter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);

        // TODO move to Login button click event later
        presenter.login();
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return presenter;
    }

    @Override
    public String getEmail() {
        // TODO replace with EditText.getText().toString() later
        return "ned@flanders.com";
    }

    @Override
    public String getPassword() {
        // TODO replace with EditText.getText().toString() later
        return "P@ssw0rd";
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoginFailed() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showLoginSuccess() {
        // TODO redirect to home screen
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
