package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.ForgotPasswordDialogFragment;
import sanmateo.com.profileapp.fragments.LoginDialogFragment;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;


/**
 * Created by rsbulanon on 10/2/16.
 */
public class LoginActivity extends BaseActivity implements OnApiRequestListener, SurfaceHolder.Callback {

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    private ApiRequestHelper apiRequestHelper;
    private static final int REQUEST_PERMISSIONS = 1;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mp;
    private RealmHelper<AuthResponse> realmHelper = new RealmHelper<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_video);
        ButterKnife.bind(this);

        if (!isNetworkAvailable()) {
            showConfirmDialog("", "San Mateo Admin App", AppConstants.WARN_CONNECTION, "Close", "",
                    new OnConfirmDialogListener() {
                        @Override
                        public void onConfirmed(String action) {
                            finish();
                            System.exit(0);
                        }

                        @Override
                        public void onCancelled(String action) {

                        }
                    });
        }

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String[] requiredPermission = new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.SEND_SMS
            };
            requestPermissions(requiredPermission, REQUEST_PERMISSIONS);
        } else {
            initialize();
        }
    }

    private void initialize() {
        AppConstants.IS_FACEBOOK_APP_INSTALLED = isFacebookInstalled();
        final AuthResponse authResponse = realmHelper.findOne(AuthResponse.class);

        if (authResponse != null) {
            CurrentUserSingleton.getInstance().setCurrentUser(authResponse);
            moveToHome();
        } else {
            apiRequestHelper = new ApiRequestHelper(this);
        }
    }

    @OnClick(R.id.btn_sign_in)
    public void showLoginDialogFragment() {
        if (isNetworkAvailable()) {
            final LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();
            loginDialogFragment.setOnLoginListener((email, password) -> {
                loginDialogFragment.dismiss();
                apiRequestHelper.authenticateUser(email, password);
            });
            loginDialogFragment.show(getFragmentManager(), "login");
        } else {
            showSnackbar(btnSignIn, AppConstants.WARN_CONNECTION);
        }
    }

    @OnClick(R.id.btn_create_account)
    public void showRegistrationPage() {
        moveToOtherActivity(RegistrationActivity.class);
    }

    @OnClick(R.id.tv_forgot_password)
    public void showForgotPassword() {
        final ForgotPasswordDialogFragment forgotPasswordDialogFragment =
                ForgotPasswordDialogFragment.newInstance();
        forgotPasswordDialogFragment.setOnForgotPasswordListener(email -> {
            forgotPasswordDialogFragment.dismiss();
            if (isNetworkAvailable()) {
                apiRequestHelper.forgotPassword(email);
            } else {
                showConfirmDialog("", "Connection Error", AppConstants.WARN_CONNECTION, "Close" , "", null);
            }
        });
        forgotPasswordDialogFragment.show(getFragmentManager(), "forgot");
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.POST_AUTH)) {
            showCustomProgress("Logging in, Please wait...");
        } else {
            showCustomProgress("Sending request, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.POST_AUTH)) {
            final AuthResponse authResponse = (AuthResponse) result;
            if (authResponse.getUserLevel().equals("superadmin") ||
                    authResponse.getUserLevel().equals("admin")) {
                showSnackbar(btnSignIn, AppConstants.WARN_INVALID_ACCOUNT);
            } else {
                //DaoHelper.saveCurrentUser(authResponse);
                realmHelper.createRecord(authResponse);
                CurrentUserSingleton.getInstance().setCurrentUser(authResponse);
                moveToHome();
            }
        } else if (action.equals(ApiAction.POST_FORGOT_PASSWORD)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showConfirmDialog("", "Forgot Password", genericMessage.getMessage(), "Close", "", null);
        }
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err", "error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(ApiAction.POST_AUTH) || action.equals(ApiAction.POST_FORGOT_PASSWORD)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog("", "Login Failed", apiError.getMessage(), "Close", "", null);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                final boolean writeExternalPermitted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                final boolean readContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                final boolean cameraPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                final boolean readSMSPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                final boolean sendSMSPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                if (writeExternalPermitted && readContactsPermission && cameraPermission
                        && readSMSPermission && sendSMSPermission) {
                    initialize();
                } else {
                    /** close the app since the user denied the required permissions */
                    showConfirmDialog("", "Permission Denied", "You need to grant San Mateo Profile " +
                            "   app with full permissions to use the app.", "Close", "", new OnConfirmDialogListener() {
                        @Override
                        public void onConfirmed(String action) {
                            finish();
                            System.exit(0);
                        }

                        @Override
                        public void onCancelled(String action) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        playVideo();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mp != null) {
            playVideo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null) {
            mp.pause();
            mp = null;
        }
    }

    private void moveToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        animateToLeft(this);
        finish();
    }

    private void playVideo() {
        mp = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            mp = new MediaPlayer();
            LogHelper.log("video", "play video");
            final Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                    + R.raw.san_mateo_avp);
            try {
                mp.setDataSource(LoginActivity.this, video);
                mp.prepare();
            } catch (IOException e) {
                LogHelper.log("video", "error inflating video background --> " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mp = MediaPlayer.create(this, R.raw.san_mateo_avp);
        }
        mp.setDisplay(surfaceHolder);

        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        //Get the SurfaceView layout parameters
        final android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

        //Set the width of the SurfaceView to the width of the screen
        lp.width = size.x;

        //Set the height of the SurfaceView to match the aspect ratio of the video
        //be sure to cast these as floats otherwise the calculation will likely be 0
        //lp.height = (int) (((float)videoHeight / (float)videoWidth) * (float)size.x);
        lp.height = size.y;

        //Commit the layout parameters
        surfaceView.setLayoutParams(lp);

        //Start video
        mp.start();
        mp.setLooping(true);
    }

    @Override
    public void onBackPressed() {
        showConfirmDialog("", "Close App", "Are you sure you want to close the app?", "Yes", "No",
                new OnConfirmDialogListener() {
                    @Override
                    public void onConfirmed(String action) {
                        System.exit(0);
                        finish();
                    }

                    @Override
                    public void onCancelled(String action) {

                    }
                });
    }
}
