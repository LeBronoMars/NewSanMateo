package sanmateo.com.profileapp.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.IncidentAddImageFragment;
import sanmateo.com.profileapp.fragments.IncidentFilingFragment;
import sanmateo.com.profileapp.fragments.SelectIncidentFragment;
import sanmateo.com.profileapp.helpers.AmazonS3Helper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.PicassoHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.interfaces.OnS3UploadListener;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;

/**
 * Created by USER on 10/14/2017.
 */

public class FileIncidentActivity extends BaseActivity implements OnItemSelectedListener,
        OnConnectionFailedListener, ConnectionCallbacks, LocationListener, OnS3UploadListener,
        OnApiRequestListener{

    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;

    private Unbinder unbinder;
    private ArrayList<String> incidentFilingList = new ArrayList<>();
    private boolean disabledCapture, reportValid;

    private GoogleApiClient googleApiClient;
    private ApiRequestHelper apiRequestHelper;

    private CurrentUserSingleton currentUserSingleton;
    private String token;

    private double lati;
    private double longi;

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

    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    @BindView(R.id.iv_send)
    ImageView ivSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_incident);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);

        ivLocator.getDrawable().setAlpha(128);
        initAmazonS3Helper(this);
        apiRequestHelper = new ApiRequestHelper(this);

        currentUserSingleton = CurrentUserSingleton.getInstance();
        token = currentUserSingleton.getCurrentUser().getToken();

        initReports();
        initLocation();
        textInputValidation();
        invalidateReport();
    }

    private void invalidateReport() {
        ivSend.getDrawable().setAlpha(128);
        reportValid = false;
    }

    private void checkValidation() {
        if (!incidentType.isEmpty()) {
            if (!isGpsConnected()) {
                if (!disabledCapture) {
                    if (!etReportOnline.getText().toString().isEmpty() && !etLocation.getText().toString().isEmpty() && isNetworkAvailable()) {
                        validateReport();
                    } else {
                        invalidateReport();
                    }
                } else {
                    if (!etReportSms.getText().toString().isEmpty() && !etLocation.getText().toString().isEmpty()) {
                        validateReport();
                    } else {
                        invalidateReport();
                    }
                }
            } else {
                if (!disabledCapture) {
                    if (!etReportOnline.getText().toString().isEmpty() && isNetworkAvailable()) {
                        validateReport();
                    } else {
                        invalidateReport();
                    }
                } else {
                    if (!etReportSms.getText().toString().isEmpty() && !etLocation.getText().toString().isEmpty()) {
                        validateReport();
                    } else {
                        invalidateReport();
                    }
                }
            }
        } else {
            invalidateReport();
        }
        Log.d("valido", "---------------validation: " + reportValid);
        Log.d("valido", "sms report empty: "  + etReportSms.getText().toString().isEmpty());
        Log.d("valido", "online report empty: "  + etReportOnline.getText().toString().isEmpty());
        Log.d("valido", "location empty: "  + etLocation.getText().toString().isEmpty());
        Log.d("valido", "network: "  + isNetworkAvailable());
        Log.d("valido", "gps: "  + isGpsConnected());
    }

    private void validateReport() {
        ivSend.getDrawable().setAlpha(255);
        reportValid = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    private void registerInternetCheckReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            alertSnackBar();
        }
    };

    private Snackbar snackbar;

    private void alertSnackBar() {
        hideSoftKeyboard();
        checkValidation();
        if (!isNetworkAvailable()) {
            if (!disabledCapture) {
                snackbar = Snackbar.make(llContainer, "You are offline.", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("SWITCH TO SMS MODE", v -> {
                    setFilingType(getString(R.string.sms_mode));
                    setLocator(getString(R.string.sms_mode));
                    initReports();
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            } else if (disabledCapture) {
                if (snackbar != null) {
                    snackbar.dismiss();
                }
            }
        } else {
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llContainer.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void initLocation() {
        if (isGpsConnected()) {
            buildGoogleApiClient();
        }
        etReportOnline.requestFocus();
        llGpsLocator.setVisibility(isGpsConnected() ? View.VISIBLE : View.GONE);
        etLocation.setVisibility(isGpsConnected() ? View.GONE : View.VISIBLE);
    }

    private void initReports() {
        etReportOnline.setVisibility(disabledCapture ? View.GONE : View.VISIBLE);
        etReportSms.setVisibility(disabledCapture ? View.VISIBLE : View.GONE);
        tvSmsCounter.setVisibility(disabledCapture ? View.VISIBLE : View.GONE);
    }

    private void textInputValidation() {
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

        etReportOnline.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void countCharacters() {
        int reportLength =  etReportSms.getText().toString().length();
        checkValidation();
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
            setFilingType(type);
            initReports();
            setLocator(type);
            alertSnackBar();
            checkValidation();
            filingFragment.dismiss();
        });
        filingFragment.show(getFragmentManager(), "FILE_INCIDENT");
    }

    private void setLocator(final String type) {
        boolean isOnlineMode = type.equals(getString(R.string.online_mode));
        if (isGpsConnected() && isOnlineMode) {
            etReportOnline.setText(etReportSms.getText().toString());
            etLocation.setText("");
            etLocation.setVisibility(View.GONE);
            llGpsLocator.setVisibility(View.VISIBLE);
            tvLocation.setTextColor(Color.DKGRAY);
            tvLocation.setText(getString(R.string.detecting_location));
            buildGoogleApiClient();
        } else {
            etReportSms.setText(etReportOnline.getText().toString());
            etLocation.setVisibility(View.VISIBLE);
            tvSmsCounter.setText(etReportSms.getText().toString().length()+ "/120");
            llGpsLocator.setVisibility(View.GONE);
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
                fileUri = data.getData();
                fileToUpload = getFile(fileUri, fileName + ".jpg");
                tvImageName.setText(fileName + ".jpg");
                PicassoHelper.loadImageFromUri(fileUri, ivReportImage, pbReportImage);
            } else {
                rlReportImage.setVisibility(View.VISIBLE);
                PicassoHelper.loadImageFromUri(fileUri, ivReportImage, pbReportImage);
                tvImageName.setText(fileToUpload.getName());
                fileToUpload = rotateBitmap(fileUri.getPath());
            }
        }
    }

    @OnClick(R.id.iv_remove_image)
    public void removeImage() {
        rlReportImage.setVisibility(View.GONE);
        deleteReport(fileToUpload);
        fileToUpload = null;
        fileUri = null;
    }

    private void deleteReport(File f) {
        f.delete();
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

    private String incidentType = "";

    private void setIncidentSelection(final int index) {
        switch (index) {
            case 0:
                incidentType = Incident.TRAFFIC_ROAD;
                tvIncidentType.setText(getString(R.string.traffic_road));
                break;
            case 1:
                incidentType = Incident.SOLID_WASTE;
                tvIncidentType.setText(getString(R.string.solid_waste));
                break;
            case 2:
                incidentType = Incident.FLOODING;
                tvIncidentType.setText(getString(R.string.flooding));
                break;
            case 3:
                incidentType = Incident.FIRE;
                tvIncidentType.setText(getString(R.string.fire));
                break;
            case 4:
                incidentType = Incident.MISCELLANEOUS;
                tvIncidentType.setText(getString(R.string.miscellaneous));
                break;
        }
        checkValidation();
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
        locationRequest.setInterval(60000); // update location every 3 seconds

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            lati = location.getLatitude();
            longi = location.getLongitude();
            LatLng latLng = new LatLng(lati, longi);
            String ll = lati + "," + longi;
            String address = getAddress(latLng);
            String locationAddress = address.isEmpty() ? ll : address;
            progressBar.setVisibility(View.GONE);
            tvLocation.setTextColor(ContextCompat.getColor(this, R.color.read_more_color));
            tvLocation.setText(locationAddress);
            ivLocator.getDrawable().setAlpha(255);
        }
    }

    private String getAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> matches = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
            return bestMatch.getAddressLine(0);
        } catch (IOException e) {
            return "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    @OnClick(R.id.iv_send)
    public void reportIncident() {
        if (reportValid) {
            if (disabledCapture) {
                sendSMS();
            } else {
                if (fileToUpload != null) {
                    uploadImageToS3(AppConstants.BUCKET_INCIDENTS, fileToUpload, 1, 1);
                } else {
                    sendOnline();
                }
            }
        }
    }

    private void sendOnline() {
        double latitude = 1;
        double longitude = 1;
        String address = etLocation.getText().toString();
        String description = etReportOnline.getText().toString();
        if (isGpsConnected()) {
            latitude = lati;
            longitude = longi;
            address = tvLocation.getText().toString();
        }
        if (fileToUpload != null) {
            showToast("has image");
            uploadImageToS3(AppConstants.BUCKET_INCIDENTS, fileToUpload, 1, 1);
        } else {
            apiRequestHelper.fileIncidentReport(token, address,
                    description, incidentType, latitude, longitude,
                    currentUserSingleton.getCurrentUser().getId(), "");
        }
    }

    private void sendSMS() {
        String phoneNo = "09062008844";
        String report = etReportSms.getText().toString();
        String address = etLocation.getText().toString();
        String msgBody = "incident_type: " + incidentType + "\nincident_address: " + address +
                "\nincident_description: " + report;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msgBody, null, null);
            showSuccessDialog();
        } catch (Exception ex) {
            showSnackbar(llContainer, ex.getMessage());
        }
    }

    private void showSuccessDialog() {
        showNonCancelableConfirmDialog("", getString(R.string.report_sent),
                getString(R.string.report_sent_content),
                getString(R.string.report_sent_confirm), null, new OnConfirmDialogListener() {
                    @Override
                    public void onConfirmed(String action) {
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(String action) {

                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("lokal", "location: " + location);
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        double latitude = 1;
        double longitude = 1;
        String address = etLocation.getText().toString();
        String description = etReportOnline.getText().toString();
        if (isGpsConnected()) {
            latitude = lati;
            longitude = longi;
            address = tvLocation.getText().toString();
        }
        deleteReport(fileToUpload);
        apiRequestHelper.fileIncidentReport(token, address, description, incidentType, latitude,
                longitude, currentUserSingleton.getCurrentUser().getId(), imageUrl);
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.POST_INCIDENT_REPORT)) {
            showCustomProgress("Filing your incident report, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        showSuccessDialog();
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        showSnackbar(llContainer, t.getMessage());
    }
}
