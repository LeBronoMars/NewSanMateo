package sanmateo.com.profileapp.incident.list.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import sanmateo.com.profileapp.incident.list.view.IncidentListView;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentListPresenter extends MvpPresenter<IncidentListView> {

    void loadIncidents(int start, int limit);
}
