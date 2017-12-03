package sanmateo.com.profileapp.incident.list.view;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

import sanmateo.com.profileapp.incident.list.presenter.IncidentListPresenter;
import sanmateo.com.profileapp.incident.usecase.Incident;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class IncidentListFragment extends MvpFragment<IncidentListView, IncidentListPresenter>
    implements IncidentListView {

    @Inject
    IncidentListPresenter presenter;

    @Override
    public IncidentListPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showIncidents(List<Incident> incidents) {

    }

    @Override
    public void showProgress() {

    }
}
