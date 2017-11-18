package sanmateo.com.profileapp.waterlevel.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.view.WaterLevelView;

/**
 * Created by rsbulanon on 19/11/2017.
 */

public class DefaultWaterLevelPresenter extends MvpBasePresenter<WaterLevelView>
    implements WaterLevelPresenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RxSchedulerUtils rxSchedulerUtils;

    private WaterLevelLoader waterLevelLoader;

    private WaterLevelView view;

    public DefaultWaterLevelPresenter(RxSchedulerUtils rxSchedulerUtils,
                                      WaterLevelLoader waterLevelLoader) {
        this.rxSchedulerUtils = rxSchedulerUtils;
        this.waterLevelLoader = waterLevelLoader;
    }

    @Override
    public void attachView(WaterLevelView view) {
        super.attachView(view);
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        dispose();
    }

    private void dispose() {
        if (null != compositeDisposable && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void loadWaterLevel(String area) {
        view.showProgress(area);
        waterLevelLoader.loadWaterLevels(area)
                        .compose(rxSchedulerUtils.mayBeAsyncSchedulerTransformer())
                        .subscribe(new MaybeObserver<List<WaterLevel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(List<WaterLevel> waterLevels) {
                                view.hideProgress(area);
                                view.showWaterLevels(area, waterLevels);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress(area);
                                view.showError(area);
                                dispose();
                            }

                            @Override
                            public void onComplete() {
                                dispose();
                            }
                        });
    }
}
