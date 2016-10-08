package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.BannerAdapter;
import sanmateo.com.profileapp.adapters.NewsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.BannerFragment;
import sanmateo.com.profileapp.fragments.ChangePasswordDialogFragment;
import sanmateo.com.profileapp.fragments.CustomBottomSheetDialogFragment;
import sanmateo.com.profileapp.fragments.DisasterMgtMenuDialogFragment;
import sanmateo.com.profileapp.fragments.ETextSiMayorDialogFragment;
import sanmateo.com.profileapp.fragments.MayorMessageDialogFragment;
import sanmateo.com.profileapp.fragments.SanMateoBannerFragment;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppBarStateListener;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.PicassoHelper;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.interfaces.OnS3UploadListener;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.models.response.News;
import sanmateo.com.profileapp.services.PusherService;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.IncidentsSingleton;
import sanmateo.com.profileapp.singletons.NewsSingleton;

/**
 * Created by rsbulanon on 7/12/16.
 */
public class HomeActivity extends BaseActivity implements OnApiRequestListener, OnS3UploadListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.vp_banner)
    ViewPager vp_banner;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rvHomeMenu)
    RecyclerView rvHomeMenu;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tvNotification)
    TextView tvNotification;
    @BindView(R.id.llHeader)
    LinearLayout llHeader;
    @BindString(R.string.disaster_mgmt)
    String headerDisasterManagement;
    @BindString(R.string.message_alert_notifications)
    String headerAlertNotifications;
    @BindDimen(R.dimen._90sdp)
    int profilePicSize;
    private ImageView iv_profile_image;
    private ProgressBar pb_load_image;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;
    private NewsSingleton newsSingleton;
    private IncidentsSingleton incidentsSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;
    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private Uri fileUri;
    private File fileToUpload;
    private String uploadToBucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPanicContact();
        initAmazonS3Helper(this);
        currentUserSingleton = CurrentUserSingleton.getInstance();
        incidentsSingleton = IncidentsSingleton.getInstance();
        newsSingleton = NewsSingleton.getInstance();
        apiRequestHelper = new ApiRequestHelper(this);
        token = currentUserSingleton.getCurrentUser().getToken();
        Log.d("token", token);
        animateBanners();
        initNavigationDrawer();
        initNews();

        if (!isMyServiceRunning(PusherService.class)) {
            startService(new Intent(this, PusherService.class));
        }

        if (newsSingleton.getNewsPrevious().size() == 0) {
            apiRequestHelper.getNews(token, 0, 10, "active", null);
        }

        initAppBarLayoutListener();

        /** display notification if there are any */
        if (PrefsHelper.getBoolean(this, "has_notifications")) {
            tvNotification.setVisibility(View.VISIBLE);
        }
    }

    private void animateBanners() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(SanMateoBannerFragment.newInstance());
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this, R.drawable.image_1)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this, R.drawable.image_2)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this, R.drawable.image_3)));
        vp_banner.setAdapter(new BannerAdapter(getSupportFragmentManager(), fragments));
        vp_banner.setOffscreenPageLimit(fragments.size());
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
        final View view = getLayoutInflater().inflate(R.layout.navigation_header, null, false);
        iv_profile_image = (ImageView) view.findViewById(R.id.iv_profile_image);
        pb_load_image = (ProgressBar) view.findViewById(R.id.pb_load_image);
        final TextView tv_profile_name = (TextView) view.findViewById(R.id.tv_profile_name);

        final int screenHeight = getScreenDimension("height");
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ((int) (screenHeight * .4)));

        iv_profile_image.setOnClickListener(view1 -> showChangeProfilePicMenu());

        navigationView.addHeaderView(view);
        navigationView.inflateMenu(R.menu.menu_side_drawer);
        navigationView.getHeaderView(0).setLayoutParams(params);

        PicassoHelper.loadImageFromURL(currentUserSingleton.getCurrentUser().getPicUrl(),
                profilePicSize, Color.TRANSPARENT, iv_profile_image, pb_load_image);

        tv_profile_name.setText(currentUserSingleton.getCurrentUser().getFirstName() + " " +
                currentUserSingleton.getCurrentUser().getLastName());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_panic_emergency:
                        setPanicContacts();
                        break;
                    case R.id.menu_incident_report:
                        moveToOtherActivity(IncidentsActivity.class);
                        break;
                    case R.id.menu_information:
                        moveToOtherActivity(InformationActivity.class);
                        break;
                    case R.id.menu_map:
                        moveToOtherActivity(MapActivity.class);
                        break;
                    case R.id.menu_directories:
                        moveToOtherActivity(DirectoriesActivity.class);
                        break;
                    case R.id.menu_gallery:
                        moveToOtherActivity(GalleryActivity.class);
                        break;
                    case R.id.menu_social_media:
                        if (isNetworkAvailable()) {
                            moveToOtherActivity(SocialMediaActivity.class);
                        } else {
                            showToast(AppConstants.WARN_CONNECTION);
                        }
                        break;
                    case R.id.menu_disaster_management:
                        final ArrayList<String> menu = new ArrayList<>();
                        menu.add("Public Announcements");
                        menu.add("Typhoon Watch");
                        menu.add("Water Level Monitoring");
                        menu.add("Alert Level(Water Level)");
                        menu.add("Global Disaster Monitoring");
                        menu.add("Emergency Numbers");
                        menu.add("Emergency Kit");
                        menu.add("How to CPR");
                        menu.add("Disaster 101");
                        final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment
                                .newInstance(headerDisasterManagement, menu);
                        fragment.setOnSelectDisasterMenuListener(new DisasterMgtMenuDialogFragment.OnSelectDisasterMenuListener() {
                            @Override
                            public void onSelectedMenu(int position) {
                                if (position == 0) {
                                    moveToOtherActivity(PublicAnnouncementsActivity.class);
                                } else if (position == 1) {
                                    moveToOtherActivity(TyphoonWatchActivity.class);
                                } else if (position == 2) {
                                    moveToOtherActivity(WaterLevelMonitoringActivity.class);
                                } else if (position == 3) {
                                    moveToOtherActivity(AlertLevelActivity.class);
                                } else if (position == 4) {
                                    moveToOtherActivity(GlobalDisasterActivity.class);
                                } else if (position == 5) {
                                    moveToOtherActivity(HotlinesActivity.class);
                                } else if (position == 6) {
                                    moveToOtherActivity(EmergencyKitActivity.class);
                                } else if (position == 7) {
                                    moveToOtherActivity(CprActivity.class);
                                } else if (position == 8) {
                                    moveToOtherActivity(Disaster101Activity.class);
                                }

                            }

                            @Override
                            public void onClose() {
                                fragment.dismiss();
                            }
                        });
                        fragment.show(getFragmentManager(), "disaster menu");
                        break;
                    case R.id.menu_rate_us:
                        showToast("Rate us feature coming soon...");
                        break;
                    case R.id.menu_change_pass:
                        changePassword();
                        break;
                    case R.id.menu_logout:
                        showConfirmDialog("", "Logout", "Are you sure you want to logout from the app?",
                                "Yes", "No", new OnConfirmDialogListener() {
                                    @Override
                                    public void onConfirmed(String action) {
                                        /** clear all singletons */
                                        newsSingleton.clearAll();
                                        incidentsSingleton.clearAll();
                                        currentUserSingleton.setCurrentUser(null);
                                        PrefsHelper.setString(HomeActivity.this,
                                                AppConstants.PREFS_LOCAL_EMERGENCY_KITS, "");
                                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                        finish();
                                        animateToRight(HomeActivity.this);
                                    }

                                    @Override
                                    public void onCancelled(String action) {

                                    }
                                });
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
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

    private void initNews() {
        final NewsAdapter newsAdapter = new NewsAdapter(this, newsSingleton.getAllNews());
        newsAdapter.setOnSelectNewsListener(n -> {
            final Intent intent = new Intent(HomeActivity.this, NewsFullPreviewActivity.class);
            intent.putExtra("news", n);
            startActivity(intent);
            animateToLeft(HomeActivity.this);
        });
        rvHomeMenu.setAdapter(newsAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvHomeMenu.setLayoutManager(linearLayoutManager);
        rvHomeMenu.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                apiRequestHelper.getNews(token, newsSingleton.getAllNews().size(), 10, "active", null);
            }
        });
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.GET_NEWS)) {
            showCustomProgress("Fetching news, Please wait...");
        } else if (action.equals(ApiAction.PUT_CHANGE_PW)) {
            showCustomProgress("Changing password, Please wait...");
        } else if (action.equals(ApiAction.PUT_CHANGE_PROFILE_PIC)) {
            showCustomProgress("Changing your profile pic, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.GET_NEWS)) {
            final ArrayList<News> news = (ArrayList<News>) result;
            newsSingleton.getAllNews().addAll(news);
        } else if (action.equals(ApiAction.GET_NEWS_BY_ID)) {
            final News news = (News) result;
            newsSingleton.getAllNews().add(0, news);
        } else if (action.equals(ApiAction.PUT_CHANGE_PW)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showToast(genericMessage.getMessage());
        } else if (action.equals(ApiAction.PUT_CHANGE_PW)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showToast("You have successfully changed your profile pic");
            /** save new profile pic url */
            fileToUpload = null;
            fileUri = null;
            PicassoHelper.loadImageFromURL(currentUserSingleton.getCurrentUser().getPicUrl(),
                    profilePicSize, Color.TRANSPARENT, iv_profile_image, pb_load_image);
        }

        if (!action.equals(ApiAction.PUT_CHANGE_PW)) {
            rvHomeMenu.getAdapter().notifyDataSetChanged();
            rvHomeMenu.smoothScrollToPosition(0);
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

    @Subscribe
    public void handleResponse(final HashMap<String, Object> map) {
        if (map.containsKey("data")) {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    if (json.getString("action").equals("news created")) {
                        apiRequestHelper.getNewsById(token, json.getInt("id"));
                    } else if (json.getString("action").equals("announcements") ||
                            json.getString("action").equals("water level")) {
                        runOnUiThread(() -> {
                            if (!tvNotification.isShown()) {
                                tvNotification.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    @OnClick(R.id.ivMayorImage)
    public void showMayorImage() {
        final MayorMessageDialogFragment fragment = MayorMessageDialogFragment.newInstance();
        fragment.show(getFragmentManager(), "mayor message");
    }

    private void initAppBarLayoutListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state.name().equals("COLLAPSED")) {
                    llHeader.setVisibility(View.VISIBLE);
                } else {
                    if (llHeader.isShown()) {
                        llHeader.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    @OnClick(R.id.rlNotifications)
    public void showNotifications() {
        final ArrayList<String> menu = new ArrayList<>();
        menu.add("Public Announcements");
        menu.add("Water Level Monitoring");
        final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment
                .newInstance(headerAlertNotifications, menu);
        fragment.setOnSelectDisasterMenuListener(new DisasterMgtMenuDialogFragment.OnSelectDisasterMenuListener() {
            @Override
            public void onSelectedMenu(int position) {
                if (tvNotification.isShown()) {
                    tvNotification.setVisibility(View.INVISIBLE);
                    PrefsHelper.setBoolean(HomeActivity.this, "has_notifications", false);
                }
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

    private void showChangeProfilePicMenu() {
        uploadToBucket = AppConstants.BUCKET_PROFILE_PIC;
        CustomBottomSheetDialogFragment.newInstance().show(getSupportFragmentManager(), "bottom sheet");
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
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_PROFILE_PIC)) {
            /** upload new pic url */
            apiRequestHelper.changeProfilePic(token, currentUserSingleton
                    .getCurrentUser().getUserId(), imageUrl);
        }
        uploadToBucket = "";
    }

    @OnClick(R.id.btnSMS)
    public void iTextSiMayor() {
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
                sendSMS("09255804848", builder.toString());
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "sms");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        /** display notification if there are any */
        if (PrefsHelper.getBoolean(this, "has_notifications")) {
            tvNotification.setVisibility(View.VISIBLE);
        } else {
            tvNotification.setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }
}
