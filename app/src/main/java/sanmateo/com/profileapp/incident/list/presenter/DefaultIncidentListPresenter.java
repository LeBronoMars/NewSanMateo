package sanmateo.com.profileapp.incident.list.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.incident.list.view.IncidentListView;
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

    }

    @Override
    public void detachView(boolean retainInstance) {
        dispose();
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed()) disposable.dispose();
    }
}
