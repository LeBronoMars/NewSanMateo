package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.models.response.Incident;

/**
 * Created by USER on 10/15/2017.
 */

public class IncidentReportFragment extends Fragment {

    private Unbinder unbinder;
    private ArrayList<Incident> incidents = new ArrayList<>();

    public static IncidentReportFragment newInstance(String type, ArrayList<Incident> incidents) {
        final IncidentReportFragment fragment = new IncidentReportFragment();
        fragment.incidents = filterByType(type, incidents);
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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
