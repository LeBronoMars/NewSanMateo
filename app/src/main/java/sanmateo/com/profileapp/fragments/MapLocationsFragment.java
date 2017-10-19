package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.MapActivity;
import sanmateo.com.profileapp.adapters.LocationsAdapter;
import sanmateo.com.profileapp.models.others.Location;


/**
 * Created by ctmanalo on 7/19/16.
 */
public class MapLocationsFragment extends Fragment {

    @BindView(R.id.rv_location)
    RecyclerView rv_location;
    private MapActivity activity;
    private ArrayList<Location> locations;

    public static MapLocationsFragment newInstance(final ArrayList<Location> locations) {
        final MapLocationsFragment fragment = new MapLocationsFragment();
        fragment.locations = locations;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MapActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_map_location, container, false);
        ButterKnife.bind(this, view);
        initRecycler();
        return view;
    }

    private void initRecycler() {
        final LocationsAdapter adapter = new LocationsAdapter(getActivity(), locations);
        rv_location.setAdapter(adapter);
        rv_location.setLayoutManager(new LinearLayoutManager(activity));
    }
}
