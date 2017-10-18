package sanmateo.com.profileapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.IncidentAddImageFragment;
import sanmateo.com.profileapp.fragments.IncidentFilingFragment;
import sanmateo.com.profileapp.fragments.SelectIncidentFragment;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.PicassoHelper;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by USER on 10/14/2017.
 */

public class FileIncidentActivity extends BaseActivity implements OnItemSelectedListener,
        OnConnectionFailedListener, ConnectionCallbacks, LocationListener{

    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;

    private Unbinder unbinder;
    private ArrayList<String> incidentFilingList = new ArrayList<>();
    private boolean disabledCapture;

    private GoogleApiClient googleApiClient;

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

    @BindView(R.id.iv_capture)
    ImageView ivCapture;

    @BindView(R.id.et_report_sms)
    EditText etReportSms;

    @BindView(R.id.et_report_online)
    EditText etReportOnline;

    @BindView(R.id.tv_sms_counter)
    TextView tvSmsCounter;

    @BindView(R.id.iv_report_image)
    ImageView ivReportImage;

    @BindView(R.id.pb_report_image)
    ProgressBar pbReportImage;

    @BindView(R.id.rl_report_image)
    RelativeLayout rlReportImage;

    @BindView(R.id.tv_image_name)
    TextView tvImageName;

    @BindView(R.id.tv_image_disabled)
    TextView tvImageDisabled;

    @BindView(R.id.ll_gps_locator)
    LinearLayout llGpsLocator;

    @BindView(R.id.et_location)
    EditText etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_incident);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);

        ivLocator.getDrawable().setAlpha(128);

        initReports();
        initLocation();
    }

    private void initLocation() {
        if (isGpsConnected()) {
            buildGoogleApiClient();
        }
        llGpsLocator.setVisibility(isGpsConnected() ? View.VISIBLE : View.GONE);
        etLocation.setVisibility(isGpsConnected() ? View.GONE : View.VISIBLE);
    }

    private void initReports() {
        etReportOnline.setVisibility(disabledCapture ? View.GONE : View.VISIBLE);
        etReportSms.setVisibility(disabledCapture ? View.VISIBLE : View.GONE);
        tvSmsCounter.setVisibility(disabledCapture ? View.VISIBLE : View.GONE);
        if (disabledCapture) {
            addReportValidation();
        }
    }

    private void addReportValidation() {
        etReportSms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countCharacters();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void countCharacters() {
        int reportLength =  etReportSms.getText().toString().length();
        tvSmsCounter.setText(reportLength + "/120");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

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
            setLocator(type);
            setFilingType(type);
            filingFragment.dismiss();
        });
        filingFragment.show(getFragmentManager(), "FILE_INCIDENT");
    }

    private void setLocator(final String type) {
        boolean isOnlineMode = type.equals(getString(R.string.online_mode));
        if (isGpsConnected() && isOnlineMode) {
            etLocation.setVisibility(View.GONE);
            llGpsLocator.setVisibility(View.VISIBLE);
            tvLocation.setTextColor(Color.DKGRAY);
            tvLocation.setText(getString(R.string.detecting_location));
            buildGoogleApiClient();
        } else {
            etLocation.setVisibility(View.VISIBLE);
            llGpsLocator.setVisibility(View.GONE);
            etLocation.setText("");
            ivLocator.getDrawable().setAlpha(128);
        }
    }

    private void setFilingType(final String type) {
        ivCapture.getDrawable().setAlpha(type.equals(getString(R.string.online_mode)) ? 255 : 128);
        disabledCapture = type.equals(getString(R.string.sms_mode));
        if (rlReportImage.getVisibility() == View.VISIBLE) {
            if (type.equals(getString(R.string.online_mode))) {
                PicassoHelper.loadImageFromUri(fileUri, ivReportImage, pbReportImage);
                tvImageDisabled.setVisibility(View.GONE);
            } else {
                PicassoHelper.loadImageFromUriGrayscale(fileUri, ivReportImage, pbReportImage);
                tvImageDisabled.setVisibility(View.VISIBLE);
            }
        }
        tvFilingType.setText(type);
        initReports();
    }

    @OnClick(R.id.iv_back)
    public void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_capture)
    public void addImage() {
        if (!disabledCapture) {
            IncidentAddImageFragment incidentAddImageFragment = IncidentAddImageFragment.newInstance();
            incidentAddImageFragment.setOnAddIncidentImageListener(new IncidentAddImageFragment.OnAddIncidentImageListener() {
                @Override
                public void captureImage() {
                    incidentAddImageFragment.dismiss();
                    useCamera();
                }

                @Override
                public void addImageFromGallery() {
                    incidentAddImageFragment.dismiss();
                    checkGallery();
                }
            });
            incidentAddImageFragment.show(getFragmentManager(), "ADD_IMAGE");
        }
    }

    private void checkGallery() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    private File fileToUpload;
    private Uri fileUri;

    private void useCamera() {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            fileToUpload = createImageFile();
            fileUri = Uri.fromFile(fileToUpload);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(cameraIntent, CAPTURE_IMAGE);
        } catch (Exception ex) {
            showConfirmDialog("", "Capture Image", "We can't get your image. Please try again.",
                    "Close", "", null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                rlReportImage.setVisibility(View.VISIBLE);
                final String fileName = "incident_image_" + getSDF().format(Calendar.getInstance().getTime());
//                getFile(data.getData(), fileName + ".jpg");
                tvImageName.setText(fileName + ".jpg");
                fileUri = data.getData();
                PicassoHelper.loadImageFromUri(data.getData(), ivReportImage, pbReportImage);
            } else {
                rlReportImage.setVisibility(View.VISIBLE);
                PicassoHelper.loadImageFromUri(fileUri, ivReportImage, pbReportImage);
                tvImageName.setText(fileToUpload.getName());
//                filesToUpload.add(activity.rotateBitmap(fileUri.getPath()));
            }
        }
    }

    @OnClick(R.id.iv_remove_image)
    public void removeImage() {
        rlReportImage.setVisibility(View.GONE);
    }

    private void deleteReport(File f) {
//        f.delete();
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
        showToast("send");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("lokal", "location: " + location);
    }
}
