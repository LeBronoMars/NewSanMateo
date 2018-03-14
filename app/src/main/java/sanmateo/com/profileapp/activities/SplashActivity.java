package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;


/**
 * Created by rsbulanon on 10/2/16.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;

    private static final int REQUEST_PERMISSIONS = 1;
    private RealmHelper<AuthResponse> realmHelper = new RealmHelper<>(AuthResponse.class);
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(statusBar);

        if (!isNetworkAvailable() && realmHelper.findOne() == null) {
            showConfirmDialog("", "San Mateo Profile App", AppConstants.WARN_OFFLINE, "Close", null, null);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final String[] requiredPermission = new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_SMS,
                        android.Manifest.permission.SEND_SMS,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                };
                requestPermissions(requiredPermission, REQUEST_PERMISSIONS);
            } else {
                initialize();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void initialize() {
        final AuthResponse authResponse = realmHelper.findOne();

        if (authResponse != null) {
            CurrentUserSingleton.getInstance().setCurrentUser(authResponse);
            moveToHome();
        } else {
            moveToOtherActivity(LoginActivity.class);
            animateToLeft(this);
            finish();
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

    private void moveToHome() {
        startActivity(new Intent(this, NewHomeActivity.class));
        animateToLeft(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
