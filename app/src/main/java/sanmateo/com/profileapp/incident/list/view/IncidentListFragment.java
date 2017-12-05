package sanmateo.com.profileapp.incident.list.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.incident.list.presenter.IncidentListPresenter;
import sanmateo.com.profileapp.incident.usecase.Incident;
import sanmateo.com.profileapp.util.glide.GlideImageLoader;


import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class IncidentListFragment extends MvpFragment<IncidentListView, IncidentListPresenter>
    implements IncidentListView {

    @BindView(R.id.incidents_loading_container)
    LinearLayout incidentsLoadingContainer;

    @BindView(R.id.incidents_multi_snap_recycler_view)
    MultiSnapRecyclerView multiSnapRecyclerView;

    @Inject
    GlideImageLoader glideImageLoader;

    @Inject
    IncidentListPresenter presenter;

    private ArrayList<Incident> incidents = new ArrayList<>();

    public static IncidentListFragment newInstance() {
        IncidentListFragment incidentListFragment = new IncidentListFragment();
        return incidentListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident_list, null);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadIncidents(0, 15);
    }

    @Override
    public IncidentListPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void hideProgress() {
        incidentsLoadingContainer.setVisibility(GONE);
        multiSnapRecyclerView.setVisibility(VISIBLE);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showIncidents(List<Incident> incidents) {
        incidentsLoadingContainer.setVisibility(incidents.isEmpty() ? VISIBLE : GONE);
        multiSnapRecyclerView.setVisibility(incidents.isEmpty() ? GONE : VISIBLE);

        this.incidents.clear();
        this.incidents.addAll(incidents);
        multiSnapRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        incidentsLoadingContainer.setVisibility(VISIBLE);
        multiSnapRecyclerView.setVisibility(GONE);
    }

    private void setUpRecyclerView() {
        multiSnapRecyclerView.setAdapter(new IncidentsAdapter(glideImageLoader, incidents));
        multiSnapRecyclerView.setLayoutManager(
            new LinearLayoutManager(getActivity(), HORIZONTAL, false));
    }
}
