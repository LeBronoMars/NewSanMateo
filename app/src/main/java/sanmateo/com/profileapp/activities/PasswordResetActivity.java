package sanmateo.com.profileapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.AppConstants;

/**
 * Created by USER on 8/28/2017.
 */

public class PasswordResetActivity extends BaseActivity {

    @BindView(R.id.frameLayout)
    RelativeLayout frameLayout;

    @BindView(R.id.rl_email_validation)
    RelativeLayout rlEmailValidation;

    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;

    @BindView(R.id.et_email)
    EditText etEmail;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void cancelActivity() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
        addEmailValidation();
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!isNetworkAvailable()) {
                hideSoftKeyboard();
                showIndefiniteSnackbar(btnResetPassword, AppConstants.WARN_OFFLINE);

//                btnSignIn.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_70));
//                btnSignIn.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_20));
//                btnSignIn.setEnabled(false);
//
//                btnCreateAccount.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_70));
//                btnCreateAccount.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_20));
//                btnCreateAccount.setEnabled(false);
//
//                tvForgotPassword.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_70));
//                tvForgotPassword.setEnabled(false);
            } else {
                dismissSnackBar();
//                checkValidation();
//
//                btnCreateAccount.setTextColor(Color.WHITE);
//                btnCreateAccount.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.button_moss_green_clickable));
//                btnCreateAccount.setEnabled(true);
//
//                tvForgotPassword.setTextColor(Color.WHITE);
//                tvForgotPassword.setEnabled(true);
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
        } else if (etEmail.getText().toString().isEmpty()) {
            rlEmailValidation.setVisibility(View.INVISIBLE);
        } else {
            rlEmailValidation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
