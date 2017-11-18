package sanmateo.com.profileapp.user.login.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.user.login.presenter.LoginPresenter;

/**
 * Created by rsbulanon on 06/11/2017.
 */

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.login_textview)
    TextView loginTextView;

    @Inject
    LoginPresenter presenter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_login);
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
        return "aa@gmail.com";
    }

    @Override
    public String getPassword() {
        // TODO replace with EditText.getText().toString() later
        return "aa";
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoginFailed() {
        Log.d("aa", "login failed");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showLoginSuccess() {
        // TODO redirect to home screen
        Log.d("app", "redirect to home");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
