package sanmateo.com.profileapp.incident.list.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import sanmateo.com.profileapp.incident.usecase.Incident;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public interface IncidentListView extends MvpView {

    void hideProgress();

    void showError();

    void showIncidents(List<Incident> incidents);

    void showProgress();
}
