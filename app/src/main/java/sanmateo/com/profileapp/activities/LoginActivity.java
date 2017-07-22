package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
import sanmateo.com.profileapp.helpers.PicassoHelper;
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

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;

    @BindView(R.id.iv_password_toggle)
    ImageView ivPasswordToggle;

    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    boolean passwordToggle;

    private ApiRequestHelper apiRequestHelper;
    private static final int REQUEST_PERMISSIONS = 1;
    private RealmHelper<AuthResponse> realmHelper = new RealmHelper<>(AuthResponse.class);
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        unbinder = ButterKnife.bind(this);

        if (!isNetworkAvailable() && realmHelper.findOne() == null) {
            showConfirmDialog("", "San Mateo Profile App", "Internet connection is required since there's " +
                            " no saved account. Please check your connection and try again.", "Close", null, null);
        } else {
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
                showConfirmDialog("", "Connection Error", AppConstants.WARN_CONNECTION, "Close", "", null);
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
                realmHelper.replaceInto(authResponse);
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

    private void moveToHome() {
        startActivity(new Intent(this, HomeActivity.class));
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
