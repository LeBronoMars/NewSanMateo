package sanmateo.com.profileapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.adapter.rxjava2.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
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
public class LoginActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.frameLayout)
    RelativeLayout frameLayout;

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;

    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;

    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;

    @BindView(R.id.iv_password_toggle)
    ImageView ivPasswordToggle;

    @BindView(R.id.et_username)
    TextInputEditText etUsername;

    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    boolean passwordToggle;
    boolean isSignInValid;

    private ApiRequestHelper apiRequestHelper;
    private static final int REQUEST_PERMISSIONS = 1;
    private RealmHelper<AuthResponse> realmHelper = new RealmHelper<>(AuthResponse.class);
    private Unbinder unbinder;

    private final static int PASSWORD_MINIMUM_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(statusBar);
        if (!isNetworkAvailable() && realmHelper.findOne() == null) {
//            showConfirmDialog("", "San Mateo Profile App", "Internet connection is required since there's " +
//                            " no saved account. Please check your connection and try again.", "Close", null, null);
            showSnackbar(btnSignIn, AppConstants.WARN_OFFLINE);
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

        addSignInValidation();
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!isNetworkAvailable()) {
                isSignInValid = false;
                hideSoftKeyboard();
                showSnackbar(btnSignIn, AppConstants.WARN_OFFLINE);
                btnSignIn.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_70));
                btnSignIn.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_20));
                btnSignIn.setEnabled(false);

                btnCreateAccount.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_70));
                btnCreateAccount.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_20));
                btnCreateAccount.setEnabled(false);

                tvForgotPassword.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_70));
                tvForgotPassword.setEnabled(false);
            } else {
                checkValidation();

                btnCreateAccount.setTextColor(Color.WHITE);
                btnCreateAccount.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.button_moss_green_clickable));
                btnCreateAccount.setEnabled(true);

                tvForgotPassword.setTextColor(Color.WHITE);
                tvForgotPassword.setEnabled(true);
            }
        }
    };

    private void registerInternetCheckReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void addSignInValidation() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkValidation() {
        if (!etUsername.getText().toString().trim().isEmpty()
                && !etPassword.getText().toString().trim().isEmpty()
                && etPassword.getText().toString().trim().length() >= PASSWORD_MINIMUM_LENGTH) {
            if (isNetworkAvailable()) {
                isSignInValid = true;
                btnSignIn.setTextColor(Color.WHITE);
                btnSignIn.setBackground(ContextCompat.getDrawable(this, R.drawable.button_light_blue_clickable));
                btnSignIn.setEnabled(true);
            } else {
                hideSoftKeyboard();
                showSnackbar(btnSignIn, AppConstants.WARN_OFFLINE);
            }
        } else {
            isSignInValid = false;
            btnSignIn.setTextColor(ContextCompat.getColor(this, R.color.transparent_70));
            btnSignIn.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_20));
            btnSignIn.setEnabled(false);
        }
    }

    public void onPasswordVisibilityToggled(View view) {
        passwordToggle = !passwordToggle;

        ivPasswordToggle.setImageDrawable(ContextCompat.getDrawable(this, passwordToggle
                ? R.drawable.ic_visibilityon_48dp : R.drawable.ic_visibilityoff_48dp));

        etPassword.setInputType(passwordToggle? InputType.TYPE_CLASS_TEXT
                :  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        etPassword.setSelection(etPassword.getText().length());
    }

    private void initialize() {
        AppConstants.IS_FACEBOOK_APP_INSTALLED = isFacebookInstalled();
        final AuthResponse authResponse = realmHelper.findOne();

        if (authResponse != null) {
            CurrentUserSingleton.getInstance().setCurrentUser(authResponse);
            moveToHome();
        } else {
            apiRequestHelper = new ApiRequestHelper(this);
        }
    }

    @OnClick(R.id.btn_sign_in)
    public void showLoginDialogFragment() {
        if (isSignInValid) {
            hideSoftKeyboard();
            if (isNetworkAvailable()) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                apiRequestHelper.authenticateUser(username, password);
            } else {
                showSnackbar(btnSignIn, AppConstants.WARN_CONNECTION_NEW);
            }
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(frameLayout.getWindowToken(), 0);
    }

    @OnClick(R.id.btn_create_account)
    public void showRegistrationPage() {
        moveToOtherActivity(NewRegistrationActivity.class);
    }

    @OnClick(R.id.tv_forgot_password)
    public void showForgotPassword() {
        if (isNetworkAvailable()) {
            moveToOtherActivity(PasswordResetActivity.class);
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.POST_AUTH)) {
            showCustomProgress("Logging in...");
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
                realmHelper.replaceInto(authResponse);
                CurrentUserSingleton.getInstance().setCurrentUser(authResponse);
                moveToHome();
            }
        } else if (action.equals(ApiAction.POST_FORGOT_PASSWORD)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showConfirmDialog("", "Forgot Password", genericMessage.getMessage(),
                              "Close", "", null);
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
                showSnackbar(btnSignIn, apiError.getMessage());
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

    private void moveToHome() {
        startActivity(new Intent(this, NewHomeActivity.class));
        animateToLeft(this);
        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
