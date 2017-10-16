package sanmateo.com.profileapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.IncidentDetailActivity;
import sanmateo.com.profileapp.adapters.IncidentReportsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.response.Incident;

/**
 * Created by USER on 10/15/2017.
 */

public class IncidentReportFragment extends Fragment {

    private Unbinder unbinder;
    private ArrayList<Incident> incidents = new ArrayList<>();
    private BaseActivity activity;

    @BindView(R.id.ll_offline_state)
    LinearLayout llOfflineState;

    @BindView(R.id.rv_incidents)
    RecyclerView rvIncidents;

    public static IncidentReportFragment newInstance(Context context, String type,
                                                     ArrayList<Incident> incidents) {
        final IncidentReportFragment fragment = new IncidentReportFragment();
        fragment.incidents = filterByType(type, incidents);
        fragment.activity = (BaseActivity)context;
        return fragment;
    }

    private static ArrayList<Incident> filterByType(String type, ArrayList<Incident> incidents) {
        ArrayList<Incident> tempIncidents = new ArrayList<>();
        for (int i = 0; i < incidents.size(); i++) {
            if (incidents.get(i).getIncidentType().equals(type)) {
                tempIncidents.add(incidents.get(i));
            }
        }
        return incidents;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident_offline, container, false);
        unbinder = ButterKnife.bind(this, view);

        initIncidents();

        return view;
    }

    private IncidentReportsAdapter adapter;

    private void initIncidents() {
        adapter = new IncidentReportsAdapter(activity, incidents);
        adapter.setOnIncidentReportListener(incident -> {
            Intent intent = new Intent(activity, IncidentDetailActivity.class);
            intent.putExtra("incident", incident);
            startActivity(intent);
            activity.animateToLeft(activity);
        });
        rvIncidents.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvIncidents.setAdapter(adapter);
        rvIncidents.scrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
