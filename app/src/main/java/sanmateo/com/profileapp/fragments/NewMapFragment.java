package sanmateo.com.profileapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.NewMapsAdapter;
import sanmateo.com.profileapp.models.others.Location;
import sanmateo.com.profileapp.singletons.BusSingleton;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by rsbulanon on 20/10/2017.
 */

public class NewMapFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.rv_locations)
    RecyclerView rvLocations;

    @BindView(R.id.tv_no_results_found)
    TextView tvNoResultsFound;

    private Unbinder unbinder;

    private SupportMapFragment mapFragment;

    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Location> duplicate = new ArrayList<>();

    private GoogleMap map;

    public static NewMapFragment newInstance(ArrayList<Location> locations) {
        NewMapFragment newMapFragment = new NewMapFragment();
        newMapFragment.locations.addAll(locations);
        newMapFragment.duplicate.addAll(locations);
        return newMapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_map, null, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.location_map);

        initMap();

        NewMapsAdapter newMapsAdapter = new NewMapsAdapter(locations);

        newMapsAdapter.setOnSelectLocationListener(new NewMapsAdapter.OnSelectLocationListener() {
            @Override
            public void onSelect(Location location) {
                focusOnMap(location.getLatLng());
            }

            @Override
            public void onCall(String phoneNumber) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        rvLocations.setAdapter(newMapsAdapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void initMap() {
        if (map == null) {
            map = mapFragment.getMap();
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.getUiSettings()
               .setScrollGesturesEnabled(true);
            map.getUiSettings()
               .setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.695179, 121.117852),
                                                             18));
            addMapMarkers(locations);
        }
    }

    private void addMapMarkers(ArrayList<Location> locations) {
        if (locations.size() > 0) {
            for (Location location : locations) {
                if (location.getLatLng() != null) {
                    double lat = location.getLatLng().latitude;
                    double longi = location.getLatLng().longitude;
                    String title = location.getLocationName();
                    String snippet = location.getLocationAddress();
                    addMapMarker(map, lat, longi, title, snippet, location.getMarker());
                }
            }
            focusOnMap(locations.get(0).getLatLng());
        }
    }

    private void focusOnMap(LatLng latLng) {
        if (map != null) {
            double lat = latLng.latitude;
            double longi = latLng.longitude;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longi), 18));
        }
    }

    private void addMapMarker(GoogleMap map, double lat, double longi, String title,
                              String snippet, int marker) {
        map.addMarker(new MarkerOptions()
                          .position(new LatLng(lat, longi))
                          .title(title)
                          .snippet(snippet)
                          .icon(marker == -1 ? null : BitmapDescriptorFactory.fromResource(marker)))
           .showInfoWindow();
    }

    @Subscribe
    public void subscribeToQuery(String query) {
        ArrayList<Location> filtered = new ArrayList<>();
        if (!query.isEmpty()) {
            for (Location location : locations) {
                if (location.getLocationName().contains(query)) {
                    filtered.add(location);
                }
            }
        }
        locations.clear();

        if (filtered.isEmpty()) {
            locations.addAll(duplicate);
        } else {
            locations.addAll(filtered);
        }

        tvNoResultsFound.setVisibility(locations.isEmpty() ? VISIBLE : GONE);
        rvLocations.setVisibility(locations.isEmpty() ? GONE : VISIBLE);

        rvLocations.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusSingleton.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusSingleton.getInstance().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
