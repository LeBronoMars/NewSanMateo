package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.TabPagerAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.AlertLevelFragment;
import sanmateo.com.profileapp.fragments.AppMarkerDialogFragment;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.models.others.AppMarker;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AlertLevelActivity extends BaseActivity implements OnMapReadyCallback, OnMarkerClickListener {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    @BindDrawable(R.drawable.bg_appbar) Drawable bgAppBar;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<AppMarker> appMarkers = new ArrayList<>();
    private GoogleMap map;
    private int tabPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_level);
        ButterKnife.bind(this);
        initMapMarker();
        initTabs();
        initMap();
        seen();
        setToolbarTitle("Water Level");
        setStatusBarColor();
        getSupportActionBar().setBackgroundDrawable(bgAppBar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        seen();
        handleArea(intent);
    }

    private void initMapMarker() {
        appMarkers.clear();
        appMarkers.add(new AppMarker(14.6969916, 121.1197769, "Montalban", "",
                AppConstants.IMAGE_URL_MARKER_MARKET_1, AppConstants.IMAGE_URL_MARKER_MARKET_2,
                AppConstants.IMAGE_URL_MARKER_MARKET_3));
        appMarkers.add(new AppMarker(14.6776636, 121.1113037, "Batasan-San Mateo Bridge", "",
                AppConstants.IMAGE_URL_MARKER_BRIDGE_1, AppConstants.IMAGE_URL_MARKER_BRIDGE_2,
                AppConstants.IMAGE_URL_MARKER_BRIDGE_3));
    }

    private void initTabs() {
        int markers = appMarkers.size();
        String[] titles = new String[markers];
        for (int i = 0; i < markers; i++) {
            fragments.add(AlertLevelFragment.newInstance(appMarkers.get(i).getTitle()));
            titles[i] = appMarkers.get(i).getTitle();
        }
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                focusOnMap(appMarkers.get(tabPosition).getLatLng());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    private void initMap() {
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        animateToRight(this);
    }

    public void focusOnMap(LatLng latLng) {
        appBarLayout.setExpanded(true);
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (map == null) {
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(appMarkers.get(0).getLatLng(), 15));
            map.setOnMarkerClickListener(this);
            initMarkers();
            handleArea(getIntent());
        }
    }

    private void initMarkers() {
        for (int i = 0; i < appMarkers.size(); i++) {
            double latitude = appMarkers.get(i).getLatitude();
            double longitude = appMarkers.get(i).getLongitude();
            String title = appMarkers.get(i).getTitle();
            String snippet = appMarkers.get(i).getSnippet();
            addMapMarker(map, latitude, longitude, title, snippet, -1);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //viewMarkerDetails(marker);
        return false;
    }

    private void viewMarkerDetails(Marker marker) {
        int tabPosition = marker.getTitle().equals("San Mateo Public Market") ? 0 : 1;
        tabLayout.getTabAt(tabPosition).select();
        final AppMarkerDialogFragment fragment = AppMarkerDialogFragment
                .newInstance(this, appMarkers.get(tabPosition));
        fragment.show(getFragmentManager(), "Marker Details");
    }

    private void seen() {
        if (PrefsHelper.getBoolean(this, "has_notifications")) {
            PrefsHelper.setBoolean(this, "has_notifications", false);
        }
    }

    private void handleArea(final Intent intent) {
        if (intent.hasExtra("area")) {
            if (intent.getStringExtra("area").equals("Batasan-San Mateo Bridge")) {
                viewPager.setCurrentItem(1);
            } else {
                viewPager.setCurrentItem(0);
            }
        }
    }
}
