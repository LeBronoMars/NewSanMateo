package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.DashboardIncidentsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.ActionsDialogFragment;
import sanmateo.com.profileapp.fragments.ChangePasswordDialogFragment;
import sanmateo.com.profileapp.fragments.CustomBottomSheetDialogFragment;
import sanmateo.com.profileapp.fragments.DisasterMgtMenuDialogFragment;
import sanmateo.com.profileapp.fragments.ETextSiMayorDialogFragment;
import sanmateo.com.profileapp.fragments.PanicSettingsDialogFragment;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.response.Announcement;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.models.response.News;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.IncidentsSingleton;

/**
 * Created by USER on 9/13/2017.
 */

public class NewHomeActivity extends BaseActivity implements OnApiRequestListener{

    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;

    @BindView(R.id.rl_action_bar)
    RelativeLayout rlActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_notify)
    ImageView ivNotify;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

//    @BindView(R.id.iv_profile_image)
//    ImageView iv_profile_image;
//
//    @BindView(R.id.lv_menu)
//    ListView lv_menu;
//
//    @BindView(R.id.iv_blur_background)
//    ImageView iv_blur_background;
//
//    @BindView(R.id.pb_load_image)
//    ProgressBar pb_load_image;
//
//    @BindView(R.id.tv_profile_name)
//    TextView tv_profile_name;

    @BindView(R.id.sv_dashboard)
    ScrollView svDashboard;

    //water level
    @BindView(R.id.tv_water_level_label)
    TextView tvWaterLevelLabel;

    @BindView(R.id.cv_water_level_bridge)
    CardView cvWaterLevelBridge;

    @BindView(R.id.tv_water_level_station_bridge)
    TextView tvWaterLevelStationBridge;

    @BindView(R.id.tv_water_level_reading_bridge)
    TextView tvWaterLevelReadingBridge;

    @BindView(R.id.cv_water_level_market)
    CardView getCvWaterLevelMarket;

    @BindView(R.id.tv_water_level_station_market)
    TextView tvWaterLevelStationMarket;

    @BindView(R.id.tv_water_level_reading_market)
    TextView tvWaterLevelReadingMarket;

    //weather
    @BindView(R.id.tv_weather_report_label)
    TextView tvWeatherReportLabel;

    @BindView(R.id.tv_weather_report_summary)
    TextView tvWeatherReportSummary;

    @BindView(R.id.tv_weather_title)
    TextView tvWeatherTitle;

    @BindView(R.id.tv_weather_temp)
    TextView tvWeatherTemp;

    //incident
    @BindView(R.id.tv_incident_label)
    TextView tvIncidentLabel;

    @BindView(R.id.rv_incidents)
    RecyclerView rvIncidents;

    @BindView(R.id.ll_incident_reports)
    LinearLayout llIncidentReports;

    @BindView(R.id.rl_nav_header)
    RelativeLayout rlNavHeader;

    @BindString(R.string.message_alert_notifications)
    String headerAlertNotifications;

    @BindDimen(R.dimen._90sdp)
    int profilePicSize;

    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;

    @BindView(R.id.tv_profile_email)
    TextView tvProfileEmail;

    private Unbinder unbinder;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String uploadToBucket;
    private Uri fileUri;
    private File fileToUpload;
    private ApiRequestHelper apiRequestHelper;
    private String token;

    private ArrayList<Incident> incidents;

    private CurrentUserSingleton currentUserSingleton;
    private IncidentsSingleton incidentsSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        setStatusBarColor(rlActionBar, navigationView, statusBar);

        currentUserSingleton = CurrentUserSingleton.getInstance();
        incidentsSingleton = IncidentsSingleton.getInstance();
        apiRequestHelper = new ApiRequestHelper(this);


        if (currentUserSingleton.getCurrentUser() == null) {
            showConfirmDialog("", "San Mateo Profile App", "Sorry, but your session has expired!",
                    "Re-login", "", new OnConfirmDialogListener() {
                        @Override
                        public void onConfirmed(String action) {
                            logout();
                        }

                        @Override
                        public void onCancelled(String action) {

                        }
                    });
        } else {
            token = currentUserSingleton.getCurrentUser().getToken();

            initNavigationDrawer();
            initDummyLabels(); //todo remove
            initIncidents();
            svDashboard.scrollTo(0,0);
        }
    }

    private void initDummyLabels() {
        //water level
        tvWaterLevelLabel.setText("Water Level as of 2:52am, October 20, 2017");
        tvWaterLevelStationMarket.setText("San Mateo Bridge");
        tvWaterLevelStationBridge.setText("Montalban");
        tvWaterLevelReadingMarket.setText("16 ft.");
        tvWaterLevelReadingBridge.setText("18 ft.");

        //weather report
        tvWeatherReportLabel.setText("Weather Report");
        tvWeatherReportSummary.setText("Partly cloudy to cloudy skies with rainshowers or thunderstorms will prevail over San Mateo");
        tvWeatherTitle.setText("Partly Cloudly");
        tvWeatherTemp.setText("31" + "\u2103");

        //incident
        tvIncidentLabel.setText("Incident Reports");

    }

    private DashboardIncidentsAdapter adapter;

    private void initIncidents() {
        incidents = new ArrayList<>();
        apiRequestHelper.getAllIncidents(token, 0, null, "active");
        initIncidentsAdapter(incidents);
    }

    @Override
    protected void onResume() {
        super.onResume();
        apiRequestHelper.getAllIncidents(token, 0, null, "active");
    }

    private void initIncidentsAdapter(ArrayList<Incident> incidents) {
        adapter = new DashboardIncidentsAdapter(this, incidents);
        adapter.setOnIncidentListener(incident -> {
            Intent intent = new Intent(NewHomeActivity.this, IncidentDetailActivity.class);
            intent.putExtra("incident", incident);
            startActivity(intent);
            animateToLeft(NewHomeActivity.this);
        });
        rvIncidents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvIncidents.setAdapter(adapter);
        rvIncidents.scrollToPosition(0);
    }

    @OnClick(R.id.tv_read_more_weather)
    public void readMoreWeather() {
        startActivity(new Intent(this, WeatherForecastActivity.class));
        animateToLeft(this);
    }

    @OnClick(R.id.tv_view_all_incidents)
    public void viewAllIncidents() {
        startActivity(new Intent(this, NewIncidentsActivity.class));
        animateToLeft(this);
    }

    @OnClick(R.id.fab_actions)
    public void showExtraActions() {
        final ActionsDialogFragment fragment = ActionsDialogFragment.newInstance();
        fragment.setOnActionSelectedListener(position -> {
            switch (position) {
                case 0:
                    textMayor();
                    break;
                case 1:
                    startActivity(new Intent(this, FileIncidentActivity.class));
                    animateToLeft(this);
                    break;
                case 2:
                    showToast("call PNP");
                    break;
                case 3:
                    showToast("call fire department");
                    break;
                case 4:
                    showToast("call ICCO");
                    break;
                case 5:
                    showToast("send sos");
                    break;
            }
        });
        fragment.show(getFragmentManager(), "FAB ACTIONS");
    }

    private void textMayor() {
        final ETextSiMayorDialogFragment fragment = ETextSiMayorDialogFragment.newInstance();
        fragment.setOnTextMayorListener(new ETextSiMayorDialogFragment.OnTextMayorListener() {
            @Override
            public void onSendText(String classification, String message) {
                fragment.dismiss();
                final StringBuilder builder = new StringBuilder();
                builder.append("Citizen Concern\n\n");
                builder.append("Classification : " + classification + "\n\n");
                builder.append("Concern : " + message + "\n\n");
                builder.append("Sent via San Mateo Profile App");
                LogHelper.log("sms", "CONCERN ---> " + builder.toString());
                sendSMS("09778397506", builder.toString(), true);
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "sms");
    }

    @OnClick(R.id.iv_notify)
    public void showNotifications() {
        final ArrayList<String> menu = new ArrayList<>();
        menu.add("Public Announcements");
        menu.add("Water Level Monitoring");
        final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment
                .newInstance(headerAlertNotifications, menu);
        fragment.setOnSelectDisasterMenuListener(new DisasterMgtMenuDialogFragment.OnSelectDisasterMenuListener() {
            @Override
            public void onSelectedMenu(int position) {
//                if (tvNotification.isShown()) {
//                    tvNotification.setVisibility(View.INVISIBLE);
//                    PrefsHelper.setBoolean(HomeActivity.this, "has_notifications", false);
//                }
                fragment.dismiss();
                if (position == 0) {
                    moveToOtherActivity(PublicAnnouncementsActivity.class);
                } else {
                    moveToOtherActivity(WaterLevelMonitoringActivity.class);
                }
            }

            @Override
            public void onClose() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "show notifications");
    }

    @OnClick(R.id.iv_burger)
    public void toggleDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
    }

    public void initNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        initSideDrawerMenu();
    }

    private void initSideDrawerMenu() {
        tvProfileName.setText(currentUserSingleton.getCurrentUser().getFirstName() + " "
                + currentUserSingleton.getCurrentUser().getLastName());
        tvProfileEmail.setText(currentUserSingleton.getCurrentUser().getEmail());
    }

    @OnClick(R.id.ll_home_dashboard)
    public void goDashboard() {
        closeDrawer();
    }

    @OnClick(R.id.ll_incidents)
    public void goIncidents() {
        viewAllIncidents();
    }

    @OnClick(R.id.ll_emergency_contact_details)
    public void goEmergency() {
        final PanicSettingsDialogFragment panicSettingsFragment = PanicSettingsDialogFragment.newInstance();
        panicSettingsFragment.setOnPanicContactListener(() -> {
            panicSettingsFragment.dismiss();
            initPanicContact();
        });
        panicSettingsFragment.show(getSupportFragmentManager(), "panic");
    }

    @OnClick(R.id.ll_directory)
    public void goDirectory() {
        startActivity(new Intent(this, DirectoriesActivity.class));
        animateToLeft(this);
    }

    @OnClick(R.id.ll_map)
    public void goMap() {
        startActivity(new Intent(this, NewMapActivity.class));
        animateToLeft(this);
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.iv_logout)
    public void exitApp() {
        closeDrawer();
        showConfirmDialog("", "Logout", "Are you sure you want to logout from the app?",
                "Yes", "No", new OnConfirmDialogListener() {
                    @Override
                    public void onConfirmed(String action) {
                        logout();
                    }

                    @Override
                    public void onCancelled(String action) {

                    }
                });
    }

    private void changePassword() {
        ChangePasswordDialogFragment fragment = ChangePasswordDialogFragment.newInstance();
        fragment.setOnChangePasswordListener((oldPassword, newPassword) ->
                apiRequestHelper.changePassword(token, currentUserSingleton.getCurrentUser().getEmail(),
                        oldPassword, newPassword));
        fragment.show(getFragmentManager(), "chane password");
    }

    private void showChangeProfilePicMenu() {
        uploadToBucket = AppConstants.BUCKET_PROFILE_PIC;
        final CustomBottomSheetDialogFragment bottomSheetDialogFragment = CustomBottomSheetDialogFragment.newInstance();
        bottomSheetDialogFragment.setOnChangeProfilePicListener(action -> {
            bottomSheetDialogFragment.dismiss();
            switch (action) {
                case "via gallery":
                    final Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
                    break;
                case "via camera":
                    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        fileToUpload = createImageFile();
                        fileUri = Uri.fromFile(fileToUpload);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                    } catch (Exception ex) {
                        showConfirmDialog("", "Capture Image",
                                "We can't get your image. Please try again.", "Close", "", null);
                    }
                    break;
            }
        });
        bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom sheet");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                fileToUpload = getFile(data.getData(), UUID.randomUUID().toString() + ".png");
                uploadFileToAmazon();
            } else if (requestCode == CAPTURE_IMAGE) {
                fileToUpload = rotateBitmap(fileUri.getPath());
                uploadFileToAmazon();
            }
        }
    }

    private void uploadFileToAmazon() {
        /** delete previous profile pic from s3 if it's not the default profile pic using gravatar */
        if (!currentUserSingleton.getCurrentUser().getPicUrl()
                .contains("http://www.gravatar.com/avatar/")) {
            deleteImage(AppConstants.BUCKET_ROOT, currentUserSingleton.getCurrentUser().getPicUrl());
        }
        uploadImageToS3(uploadToBucket, fileToUpload, 1, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.GET_NEWS)) {
            showCustomProgress("Fetching news, Please wait...");
        } else if (action.equals(ApiAction.PUT_CHANGE_PW)) {
            showCustomProgress("Changing password, Please wait...");
        } else if (action.equals(ApiAction.PUT_CHANGE_PROFILE_PIC)) {
            showCustomProgress("Changing your profile pic, Please wait...");
        } else if (action.equals(ApiAction.GET_INCIDENTS)) {
            showCustomProgress("Fetching incidents, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.GET_NEWS)) {
//            final ArrayList<News> news = (ArrayList<News>) result;
//            newsSingleton.getAllNews().clear();
//            final RealmHelper<News> realmHelper = new RealmHelper<>(News.class);
//
//            if (!news.isEmpty()) {
//                for (News n : news) {
//                    realmHelper.replaceInto(n);
//                }
//            }
//
//            if (realmHelper.count() > 0) {
//                final RealmResults<News> cachedNews = realmHelper.findAll("createdAt", Sort.DESCENDING);
//                for (News n : cachedNews) {
//                    newsSingleton.getAllNews().add(n);
//                }
//            }
//            newsSingleton.getAllNews().addAll(news);
        } else if (action.equals(ApiAction.GET_NEWS_BY_ID)) {
//            final RealmHelper<News> realmHelper = new RealmHelper<>(News.class);
//            final News news = (News) result;
//            realmHelper.replaceInto(news);
//            newsSingleton.getAllNews().add(0, news);
        } else if (action.equals(ApiAction.PUT_CHANGE_PW)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showToast(genericMessage.getMessage());
        } else if (action.equals(ApiAction.PUT_CHANGE_PROFILE_PIC)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showToast("You have successfully changed your profile pic");

            /** save new profile pic url */
            fileToUpload = null;
            fileUri = null;

            final RealmHelper<AuthResponse> realmHelper = new RealmHelper<>(AuthResponse.class);
            realmHelper.openRealm();
            currentUserSingleton.getCurrentUser().setPicUrl(genericMessage.getMessage());
            realmHelper.update(currentUserSingleton.getCurrentUser());
            realmHelper.commitTransaction();

            LogHelper.log("changePic", "new pic url ---> " + currentUserSingleton.getCurrentUser().getPicUrl());

//            PicassoHelper.loadBlurImageFromURL(this, currentUserSingleton.getCurrentUsernavi

        } else if (action.equals(ApiAction.GET_INCIDENTS)) {
            incidents = (ArrayList<Incident>) result;
            llIncidentReports.setVisibility(incidents.size() > 0 ? View.VISIBLE : View.GONE);
            initIncidentsAdapter(incidents);
            incidentsSingleton.getIncidents("active").clear();
            incidentsSingleton.getIncidents("active").addAll(incidents);
        }
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err", "error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            if (action.equals(ApiAction.PUT_CHANGE_PW)) {
                showConfirmDialog(action.name(), "Change Password Failed", apiError.getMessage(), "Close", "", null);
            }
        }
    }

    private void logout() {
        /** clear all singletons */
//        newsSingleton.clearAll();
//        incidentsSingleton.clearAll();
        currentUserSingleton.setCurrentUser(null);

        new RealmHelper<>(News.class).deleteRecords();
        new RealmHelper<>(Announcement.class).deleteRecords();
        new RealmHelper<>(AuthResponse.class).deleteRecords();

        PrefsHelper.setString(NewHomeActivity.this,
                AppConstants.PREFS_LOCAL_EMERGENCY_KITS, "");

        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        animateToRight(NewHomeActivity.this);
    }
}
