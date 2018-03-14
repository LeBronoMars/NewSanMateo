package sanmateo.com.profileapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;


import static sanmateo.com.profileapp.helpers.AppConstants.WARN_CONNECTION_NEW;
import static sanmateo.com.profileapp.helpers.AppConstants.WARN_FIELD_REQUIRED;
import static sanmateo.com.profileapp.helpers.AppConstants.WARN_PASSWORD_NOT_MATCH;

public class ChangePasswordActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.ll_action_bar)
    LinearLayout llActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.frameLayout)
    RelativeLayout frameLayout;

    @BindView(R.id.btn_change_password)
    Button btnChangePassword;

    @BindView(R.id.et_old_password)
    EditText etOldPassword;

    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    private Unbinder unbinder;
    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        unbinder = ButterKnife.bind(this);

        setStatusBarColor(llActionBar, statusBar);
        apiRequestHelper = new ApiRequestHelper(this);
    }

    @OnClick(R.id.iv_back)
    public void cancelActivity() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!isNetworkAvailable()) {
                hideSoftKeyboard();
                showIndefiniteSnackbar(btnChangePassword, AppConstants.WARN_OFFLINE);
            } else {
                dismissSnackBar();
            }
        }
    };

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(frameLayout.getWindowToken(), 0);
    }

    private void registerInternetCheckReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @OnClick(R.id.btn_change_password)
    public void changePassword() {
        if (isNetworkAvailable()) {

            String oldPassword = etOldPassword.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (oldPassword.isEmpty()) {
                setError(etOldPassword, WARN_FIELD_REQUIRED);
            } else if (newPassword.isEmpty()) {
                setError(etNewPassword, WARN_FIELD_REQUIRED);
            } else if (confirmPassword.isEmpty()) {
                setError(etConfirmPassword, WARN_FIELD_REQUIRED);
            } else if (!newPassword.equals(confirmPassword)) {
                setError(etConfirmPassword, WARN_PASSWORD_NOT_MATCH);
            } else if (newPassword.length() < 8) {
                setError(etNewPassword, getString(R.string.password_disclaimer));
            } else if (confirmPassword.length() < 8) {
                setError(etConfirmPassword, getString(R.string.password_disclaimer));
            } else {
                String token = CurrentUserSingleton.getInstance().getCurrentUser().getToken();
                String email = CurrentUserSingleton.getInstance().getCurrentUser().getEmail();

                apiRequestHelper.changePassword(token, email, oldPassword, newPassword);
            }
        } else {
            hideSoftKeyboard();
            showIndefiniteSnackbar(btnChangePassword, WARN_CONNECTION_NEW);
        }
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
        showCustomProgress("Processing, Please wait...");
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        showToast("You have successfully changed your password.");
        finish();
        animateToRight(this);
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        hideSoftKeyboard();
        dismissCustomProgress();
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            if (apiError.getMessage().equals("User record not found!")) {
                showSnackbar(btnChangePassword, getString(R.string.alert_no_such_email));
            } else {
                showSnackbar(btnChangePassword, apiError.getMessage());
            }
        } else {
            showSnackbar(btnChangePassword, t.getMessage());
        }
    }
}
