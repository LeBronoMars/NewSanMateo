package sanmateo.com.profileapp.user.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.dashboard.view.DashboardActivity;
import sanmateo.com.profileapp.user.login.presenter.LoginPresenter;


import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static sanmateo.com.profileapp.util.TextUtils.isNotNullOrEmpty;

/**
 * Created by rsbulanon on 06/11/2017.
 */

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindString(R.string.login_message_failed)
    String loginFailedMessage;

    @BindString(R.string.ok)
    String ok;

    @BindView(R.id.forgot_password)
    TextView forgotPassword;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.login_coordinator_layout)
    CoordinatorLayout loginCoordinatorLayout;

    @BindView(R.id.login_edit_text_username)
    TextView loginEditTextUsername;

    @BindView(R.id.login_edit_text_password)
    TextView loginEditTextPassword;

    @BindView(R.id.login_loading_container)
    LinearLayout loginLoadingContainer;

    @BindView(R.id.signup_button)
    Button signUpButton;

    @Inject
    LoginPresenter presenter;

    private Unbinder unbinder;

    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        monitorIfAllFieldsAreNotEmptyAndValid();
    }

    @OnClick(R.id.login_button)
    public void login() {
        presenter.login();
    }

    private void monitorIfAllFieldsAreNotEmptyAndValid() {
        // Attach an observable to each EditText so that it emits data when text has changed.
        Observable.combineLatest(getTextWatcherObservable(loginEditTextUsername),
                                 getTextWatcherObservable(loginEditTextPassword),
                                 (username, password) ->
                                     isNotNullOrEmpty(username) && isNotNullOrEmpty(password))
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(this::enableLoginButtonWhenAllRequiredFieldsAreValid);
    }

    private void enableLoginButtonWhenAllRequiredFieldsAreValid(boolean enable) {
        loginButton.setEnabled(enable);
    }

    private Observable<String> getTextWatcherObservable(@NonNull final TextView textView) {

        final PublishSubject<String> subject = PublishSubject.create();

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                subject.onNext(s.toString());
            }
        });
        return subject;
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return presenter;
    }

    @Override
    public String getEmail() {
        return loginEditTextUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return loginEditTextPassword.getText().toString();
    }

    @Override
    public void hideProgress() {
        showProgressIndicator(false);
    }

    @Override
    public void showLoginFailed() {
        snackbar = Snackbar.make(loginCoordinatorLayout, loginFailedMessage, LENGTH_INDEFINITE);
        snackbar.setAction(ok, v -> snackbar.dismiss());
        snackbar.show();
    }

    @Override
    public void showProgress() {
        showProgressIndicator(true);
    }

    @Override
    public void showLoginSuccess() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showProgressIndicator(boolean show) {
        forgotPassword.setVisibility(show ? GONE : VISIBLE);
        loginButton.setVisibility(show ? GONE : VISIBLE);
        loginEditTextPassword.setVisibility(show ? GONE : VISIBLE);
        loginEditTextUsername.setVisibility(show ? GONE : VISIBLE);
        loginLoadingContainer.setVisibility(show ? VISIBLE : GONE);
        signUpButton.setVisibility(show ? GONE : VISIBLE);
    }
}
