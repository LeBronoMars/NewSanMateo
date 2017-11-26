package sanmateo.com.profileapp.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.dashboard.view.DashboardActivity;
import sanmateo.com.profileapp.splash.presenter.SplashPresenter;
import sanmateo.com.profileapp.user.login.view.LoginActivity;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class SplashActivity extends MvpActivity<SplashView, SplashPresenter> implements SplashView {

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter.checkForLocalUser();
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return splashPresenter;
    }

    @Override
    public void onRedirectToHome() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public void onRedirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
