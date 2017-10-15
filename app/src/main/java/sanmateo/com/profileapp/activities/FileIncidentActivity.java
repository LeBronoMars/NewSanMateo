package sanmateo.com.profileapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.IncidentAddImageFragment;
import sanmateo.com.profileapp.fragments.IncidentFilingFragment;
import sanmateo.com.profileapp.fragments.SelectIncidentFragment;

/**
 * Created by USER on 10/14/2017.
 */

public class FileIncidentActivity extends BaseActivity implements OnItemSelectedListener,
        OnConnectionFailedListener, ConnectionCallbacks, LocationListener{

    private Unbinder unbinder;
    private ArrayList<String> incidentFilingList = new ArrayList<>();

    @BindView(R.id.ll_action_bar)
    LinearLayout llActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.tv_filing_type)
    TextView tvFilingType;

    @BindView(R.id.tv_incident_type)
    TextView tvIncidentType;

    @BindView(R.id.iv_incident_icon)
    ImageView ivIncidentIcon;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_location)
    TextView tvLocation;

    @BindView(R.id.iv_locator)
    ImageView ivLocator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_incident);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);

        ivLocator.getDrawable().setAlpha(128);

        buildGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    private GoogleApiClient googleApiClient;

    synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @OnClick(R.id.rl_filing_type)
    public void clickSelection(){
        IncidentFilingFragment filingFragment = IncidentFilingFragment.newInstance();
        filingFragment.setOnFilingTypeListener(type -> {
            setFilingType(type);
            filingFragment.dismiss();
        });
        filingFragment.show(getFragmentManager(), "FILE_INCIDENT");
    }

    private void setFilingType(final String type) {
        tvFilingType.setText(type);
    }

    @OnClick(R.id.iv_back)
    public void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_capture)
    public void addImage() {
        IncidentAddImageFragment incidentAddImageFragment = IncidentAddImageFragment.newInstance();
        incidentAddImageFragment.setOnAddIncidentImageListener(new IncidentAddImageFragment.OnAddIncidentImageListener() {
            @Override
            public void captureImage() {
                incidentAddImageFragment.dismiss();
                showToast("capture image");
            }

            @Override
            public void addImageFromGallery() {
                incidentAddImageFragment.dismiss();
                showToast("select image");
            }
        });
        incidentAddImageFragment.show(getFragmentManager(), "ADD_IMAGE");
    }

    @OnClick(R.id.iv_select_incident_type)
    public void selectIncidentType() {
        SelectIncidentFragment selectIncidentFragment = SelectIncidentFragment.newInstance();
        selectIncidentFragment.setOnIncidentTypeListener(index -> {
            activateIncidentType();
            selectIncidentFragment.dismiss();
            setIncidentSelection(index);
        });
        selectIncidentFragment.show(getFragmentManager(), "SELECT_INCIDENT");
    }

    private void activateIncidentType() {
        ivIncidentIcon.setVisibility(View.VISIBLE);
        tvIncidentType.setTextColor(ContextCompat.getColor(this, R.color.read_more_color));
    }

    private void setIncidentSelection(final int index) {
        switch (index) {
            case 0:
                tvIncidentType.setText(getString(R.string.traffic_road));
                break;
            case 1:
                tvIncidentType.setText(getString(R.string.solid_waste));
                break;
            case 2:
                tvIncidentType.setText(getString(R.string.flooding));
                break;
            case 3:
                tvIncidentType.setText(getString(R.string.fire));
                break;
            case 4:
                tvIncidentType.setText(getString(R.string.miscellaneous));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvFilingType.setText(incidentFilingList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000); // update location every 3 seconds

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            progressBar.setVisibility(View.GONE);
            tvLocation.setTextColor(ContextCompat.getColor(this, R.color.read_more_color));
            tvLocation.setText(location.getLatitude() + "," +location.getLongitude());
            ivLocator.getDrawable().setAlpha(255);
        }
    }

    @OnClick(R.id.iv_send)
    public void reportIncident() {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("lokal", "location: " + location);
    }
}
