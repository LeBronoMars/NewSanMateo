package sanmateo.com.profileapp.incident.list.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.incident.list.view.IncidentListView;
import sanmateo.com.profileapp.incident.usecase.Incident;
import sanmateo.com.profileapp.incident.usecase.IncidentsLoader;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DefaultIncidentListPresenter extends MvpBasePresenter<IncidentListView>
    implements IncidentListPresenter {

    private IncidentsLoader incidentsLoader;

    private RxSchedulerUtils rxSchedulerUtils;

    private IncidentListView view;

    private Disposable disposable;

    @Inject
    public DefaultIncidentListPresenter(IncidentsLoader incidentsLoader,
                                        RxSchedulerUtils rxSchedulerUtils) {
        this.incidentsLoader = incidentsLoader;
        this.rxSchedulerUtils = rxSchedulerUtils;
    }

    @Override
    public void attachView(IncidentListView view) {
        super.attachView(view);
        this.view = view;
    }

    @Override
    public void loadIncidents(int start, int limit) {
        view.showProgress();
        incidentsLoader.loadIncidents(start, limit)
                       .compose(rxSchedulerUtils.singleAsyncSchedulerTransformer())
                       .subscribe(new SingleObserver<List<Incident>>() {
                           @Override
                           public void onSubscribe(Disposable d) {
                               disposable = d;
                           }

                           @Override
                           public void onSuccess(List<Incident> incidents) {
                                Log.d("app", "on success --> " + incidents.size());
                                view.hideProgress();
                                view.showIncidents(incidents);
                           }

                           @Override
                           public void onError(Throwable e) {
                               Log.d("app", "on error --> " + e.getMessage());
                               view.hideProgress();
                               view.showError();
                           }
                       });
    }

    @Override
    public void detachView(boolean retainInstance) {
        dispose();
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed()) disposable.dispose();
    }
}
