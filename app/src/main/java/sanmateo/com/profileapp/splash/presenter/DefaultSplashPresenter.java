package sanmateo.com.profileapp.splash.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.splash.view.SplashView;
import sanmateo.com.profileapp.user.local.NoQueryResultException;
import sanmateo.com.profileapp.user.local.RoomUserLoader;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DefaultSplashPresenter extends MvpBasePresenter<SplashView>
    implements SplashPresenter {

    private RoomUserLoader roomUserLoader;

    private RxSchedulerUtils rxSchedulerUtils;

    Disposable disposable;

    private SplashView splashView;

    @Inject
    public DefaultSplashPresenter(RoomUserLoader roomUserLoader, RxSchedulerUtils rxSchedulerUtils) {
        this.roomUserLoader = roomUserLoader;
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
    public void checkForExistingUser() {
        roomUserLoader.loadCurrentUser()
                      .compose(rxSchedulerUtils.mayBeAsyncSchedulerTransformer())
                      .subscribe(new MaybeObserver<User>() {
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
                              if (e instanceof NoQueryResultException) {
                                  splashView.onRedirectToLogin();
                              }
                              dispose();
                          }

                          @Override
                          public void onComplete() {
                              dispose();
                          }
                      });
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed()) disposable.dispose();
    }
}
