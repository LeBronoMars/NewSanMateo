package sanmateo.com.profileapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.response.ApiError;

/**
 * Created by USER on 8/28/2017.
 */

public class PasswordResetActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.ll_action_bar)
    LinearLayout llActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.frameLayout)
    RelativeLayout frameLayout;

    @BindView(R.id.rl_email_validation)
    RelativeLayout rlEmailValidation;

    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;

    @BindView(R.id.et_email)
    EditText etEmail;

    private Unbinder unbinder;
    private boolean isEmailValid;
    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        unbinder = ButterKnife.bind(this);

        setStatusBarColor(llActionBar, statusBar);
        apiRequestHelper = new ApiRequestHelper(this);
        addEmailValidation();
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
                showIndefiniteSnackbar(btnResetPassword, AppConstants.WARN_OFFLINE);
            } else {
                dismissSnackBar();
            }
        }
    };

    private void validateForm() {
        if (isEmailValid) {
            btnResetPassword.setTextColor(Color.WHITE);
            btnResetPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_light_blue_clickable));
            btnResetPassword.setEnabled(true);
        } else {
            btnResetPassword.setTextColor(ContextCompat.getColor(this, R.color.transparent_dark_70));
            btnResetPassword.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_dark_20));
            btnResetPassword.setEnabled(false);
        }
    }

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

    private void addEmailValidation() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void validateEmail() {
        if (isValidEmail(etEmail.getText().toString())) {
            rlEmailValidation.setVisibility(View.INVISIBLE);
            isEmailValid = true;
        } else if (etEmail.getText().toString().isEmpty()) {
            rlEmailValidation.setVisibility(View.INVISIBLE);
            isEmailValid = false;
        } else {
            rlEmailValidation.setVisibility(View.VISIBLE);
            isEmailValid = false;
        }
        validateForm();
    }

    @OnClick(R.id.btn_reset_password)
    public void resetPassword() {
        if (isNetworkAvailable()) {
            Log.d("err", "email: " + etEmail.getText().toString());
            apiRequestHelper.forgotPassword(etEmail.getText().toString());
        } else {
            hideSoftKeyboard();
            showIndefiniteSnackbar(btnResetPassword, AppConstants.WARN_CONNECTION_NEW);
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
        showCustomProgress("Submitting information...");
        Log.d("err", "started");
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        Log.d("err", "success");
        showNonCancelableConfirmDialog("", getString(R.string.reset_password_title),
                getString(R.string.reset_password_content),
                getString(R.string.reset_password_confirm), null, new OnConfirmDialogListener() {
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
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        Log.d("err", "failed: " + t.getMessage());
        hideSoftKeyboard();
        dismissCustomProgress();
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            if (apiError.getMessage().equals("User record not found!")) {
                showSnackbar(btnResetPassword, getString(R.string.alert_no_such_email));
            } else {
                showSnackbar(btnResetPassword, apiError.getMessage());
            }
        } else {
            showSnackbar(btnResetPassword, t.getMessage());
        }
    }
}
