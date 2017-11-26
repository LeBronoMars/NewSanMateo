package sanmateo.com.profileapp.splash.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.splash.usecase.SplashLoader;
import sanmateo.com.profileapp.splash.view.SplashView;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DefaultSplashPresenter extends MvpBasePresenter<SplashView>
    implements SplashPresenter {

    private RxSchedulerUtils rxSchedulerUtils;

    private SplashLoader splashLoader;

    Disposable disposable;

    private SplashView splashView;

    @Inject
    public DefaultSplashPresenter(RxSchedulerUtils rxSchedulerUtils,
                                  SplashLoader splashLoader) {
        this.splashLoader = splashLoader;
        this.rxSchedulerUtils = rxSchedulerUtils;
    }

    @Override
    public void attachView(SplashView view) {
        super.attachView(view);
        this.splashView = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        dispose();
    }

    @Override
    public void checkForLocalUser() {
        splashLoader.loadExistingUser()
                      .compose(rxSchedulerUtils.singleAsyncSchedulerTransformer())
                      .subscribe(new SingleObserver<User>() {
                          @Override
                          public void onSubscribe(Disposable d) {
                              disposable = d;
                          }

                          @Override
                          public void onSuccess(User user) {
                              splashView.onRedirectToHome();
                          }

                          @Override
                          public void onError(Throwable e) {
                              splashView.onRedirectToLogin();
                              dispose();
                          }
                      });
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed()) disposable.dispose();
    }
}
