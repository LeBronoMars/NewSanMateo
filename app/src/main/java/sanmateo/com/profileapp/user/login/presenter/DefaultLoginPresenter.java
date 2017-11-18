package sanmateo.com.profileapp.user.login.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.user.login.model.UserLoader;
import sanmateo.com.profileapp.user.login.view.LoginView;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

/**
 * Created by rsbulanon on 06/11/2017.
 */

class DefaultLoginPresenter extends MvpBasePresenter<LoginView> implements LoginPresenter {

    CompositeDisposable compositeDisposable;

    private RxSchedulerUtils rxSchedulerUtils;

    private UserLoader userLoader;

    LoginView view;

    @Inject
    public DefaultLoginPresenter(RxSchedulerUtils rxSchedulerUtils,
                                 UserLoader userLoader) {
        this.compositeDisposable = new CompositeDisposable();
        this.rxSchedulerUtils = rxSchedulerUtils;
        this.userLoader = userLoader;
    }

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        dispose();
    }

    private void dispose() {
        Log.d("app", "dispose subscription");
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void login() {
        view.showProgress();
        userLoader.login(view.getEmail(), view.getPassword())
                  .compose(rxSchedulerUtils.singleAsyncSchedulerTransformer())
                  .subscribe(new SingleObserver<User>() {
                      @Override
                      public void onSubscribe(@NonNull Disposable d) {
                          compositeDisposable.add(d);
                      }

                      @Override
                      public void onSuccess(@NonNull User user) {
                          view.hideProgress();
                          view.showLoginSuccess();
                      }

                      @Override
                      public void onError(@NonNull Throwable e) {
                          view.hideProgress();
                          view.showLoginFailed();
                      }
                  });
    }
}
