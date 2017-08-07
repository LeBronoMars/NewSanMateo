package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.customviews.CustomSpinner;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.response.ApiError;

/**
 * Created by USER on 8/6/2017.
 */

public class NewRegistrationActivity extends BaseActivity implements OnItemSelectedListener,
        OnApiRequestListener{

    @BindView(R.id.ll_account_information)
    LinearLayout llAccountInformation;

    @BindView(R.id.ll_personal_information)
    LinearLayout llPersonalInformation;

    @BindView(R.id.iv_next)
    ImageView ivNext;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_password_toggle)
    ImageView ivPasswordToggle;

    @BindView(R.id.iv_confirm_password_toggle)
    ImageView ivConfirmPasswordToggle;

    @BindView(R.id.tv_steps)
    TextView tvSteps;

    @BindView(R.id.tv_spinner)
    TextView tvSpinner;

    @BindView(R.id.spnr_gender)
    CustomSpinner spnrGender;

    @BindView(R.id.et_first_name)
    TextInputEditText etFirstName;

    @BindView(R.id.et_last_name)
    TextInputEditText etLastName;

    @BindView(R.id.et_contact_no)
    TextInputEditText etContactNo;

    @BindView(R.id.et_address)
    TextInputEditText etAddress;

    @BindView(R.id.et_email)
    TextInputEditText etEmail;

    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @BindView(R.id.et_confirm_password)
    TextInputEditText etConfirmPassword;

    @BindView(R.id.tv_email_validation)
    TextView tvEmailValidation;

    @BindView(R.id.tv_password_disclaimer)
    TextView tvPasswordDisclaimer;

    @BindView(R.id.tv_password_validation)
    TextView tvPasswordValidation;

    @BindView(R.id.rl_email_validation)
    RelativeLayout rlEmailValidation;

    @BindView(R.id.rl_password_validation)
    RelativeLayout rlPasswordValidation;

    private Unbinder unbinder;
    boolean isPersonalValid, isEmailValid, isPasswordValid, isConfirmPasswordValid, isAccountValid;
    boolean passwordToggle, confirmPasswordToggle;
    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup);
        unbinder = ButterKnife.bind(this);
        apiRequestHelper = new ApiRequestHelper(this);

        initGenderSpinner();
        addPersonalInfoValidation();
        addAccountInfoValidation();
    }

    @OnClick(R.id.tv_spinner)
    public void showSpinner() {
        spnrGender.performClick();
    }

    @OnClick(R.id.iv_close)
    public void cancelSignUp() {
        ivBack.setVisibility(View.GONE);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (ivBack.getVisibility() == View.VISIBLE) {
            back();
        } else {
            super.onBackPressed();
        }
    }

    private void addPersonalInfoValidation() {
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePersonalInformation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePersonalInformation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etContactNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePersonalInformation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePersonalInformation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void validatePersonalInformation() {
        if (!etFirstName.getText().toString().trim().isEmpty()
                && !etLastName.getText().toString().trim().isEmpty()
                && !etContactNo.getText().toString().trim().isEmpty()
                && !etAddress.getText().toString().trim().isEmpty()
                && !tvSpinner.getText().toString().equals("Gender")) {
            isPersonalValid = true;
            ivNext.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_next_active_48dp));
        } else {
            isPersonalValid = false;
            ivNext.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_next_inactive_48dp));
        }
    }

    private void addAccountInfoValidation() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailValidation();
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
                passwordValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void emailValidation() {
        if (isValidEmail(etEmail.getText().toString())) {
            isEmailValid = true;
            rlEmailValidation.setVisibility(View.INVISIBLE);
        } else {
            isEmailValid = false;
            tvEmailValidation.setText(R.string.alert_email);
            rlEmailValidation.setVisibility(View.VISIBLE);
        }
        validateAccountInformation();
    }

    private void passwordValidation() {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (password.isEmpty() && confirmPassword.isEmpty()) {
            isPasswordValid = false;
            rlPasswordValidation.setVisibility(View.GONE);
            tvPasswordDisclaimer.setVisibility(View.VISIBLE);
            tvPasswordDisclaimer.setText(R.string.password_disclaimer);
        } else if (!password.isEmpty() && !password.matches("[a-zA-Z0-9.? ]*")) {
            isPasswordValid = false;
            tvPasswordDisclaimer.setVisibility(View.GONE);
            rlPasswordValidation.setVisibility(View.VISIBLE);
            tvPasswordValidation.setText(R.string.alert_password_extra_char);
        } else if (password.length() < 8) {
            isPasswordValid = false;
            tvPasswordDisclaimer.setVisibility(View.GONE);
            rlPasswordValidation.setVisibility(View.VISIBLE);
            tvPasswordValidation.setText(R.string.alert_password_short);
        } else if (password.length() > 12) {
            isPasswordValid = false;
            tvPasswordDisclaimer.setVisibility(View.GONE);
            rlPasswordValidation.setVisibility(View.VISIBLE);
            tvPasswordValidation.setText(R.string.alert_password_long);
        } else {
            isPasswordValid = true;
            if (confirmPassword.isEmpty()) {
                rlPasswordValidation.setVisibility(View.GONE);
                tvPasswordDisclaimer.setVisibility(View.VISIBLE);
                tvPasswordDisclaimer.setText(R.string.password_confirm);
            } else {
                confirmPasswordValidation();
            }
        }
        validateAccountInformation();
    }

    private void confirmPasswordValidation() {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (!password.isEmpty() && confirmPassword.isEmpty()) {
            passwordValidation();
        } else if (password.isEmpty() && confirmPassword.isEmpty()) {
            isConfirmPasswordValid = false;
            rlPasswordValidation.setVisibility(View.GONE);
            tvPasswordDisclaimer.setVisibility(View.VISIBLE);
            tvPasswordDisclaimer.setText(R.string.password_disclaimer);
        } else if (!password.equals(confirmPassword)) {
            isConfirmPasswordValid = false;
            tvPasswordDisclaimer.setVisibility(View.GONE);
            rlPasswordValidation.setVisibility(View.VISIBLE);
            tvPasswordValidation.setText(R.string.alert_password_not_match);
        } else if (isPasswordValid && password.equals(confirmPassword)){
            isConfirmPasswordValid = true;
            rlPasswordValidation.setVisibility(View.GONE);
            tvPasswordDisclaimer.setVisibility(View.GONE);
        }
        validateAccountInformation();
    }

    private void validateAccountInformation() {
        if (isEmailValid && isPasswordValid && isConfirmPasswordValid) {
            isAccountValid = true;
            ivNext.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_next_active_48dp));
        } else {
            isAccountValid = false;
            ivNext.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_next_inactive_48dp));
        }
    }

    @OnClick(R.id.iv_next)
    public void next() {
        if (ivBack.getVisibility() == View.GONE && isPersonalValid) {
            tvSteps.setText(R.string.registration_step_2);
            ivBack.setVisibility(View.VISIBLE);
            llPersonalInformation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_left));
            llPersonalInformation.setVisibility(View.GONE);
            llAccountInformation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_right));
            llAccountInformation.setVisibility(View.VISIBLE);
            ivNext.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_next_inactive_48dp));
        } else if (isAccountValid) {
            if (isNetworkAvailable()) {
                sendRegistration();
            } else {
                showSnackbar(ivNext, AppConstants.WARN_CONNECTION_NEW);
            }
        }
    }

    private void sendRegistration() {
        final String firstName = etFirstName.getText().toString();
        final String lastName = etLastName.getText().toString();
        final String contactNo = etContactNo.getText().toString();
        final String address = etAddress.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String gender = tvSpinner.getText().toString();
        final String userLevel = "Regular User";
        apiRequestHelper.createUser(firstName, lastName, contactNo, gender, email, address,
                userLevel, password);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        tvSteps.setText(R.string.registration_step_1);
        ivNext.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_next_active_48dp));
        llAccountInformation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_right));
        llAccountInformation.setVisibility(View.GONE);
        llPersonalInformation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_left));
        llPersonalInformation.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.GONE);
    }

    ArrayList<String> genderList = new ArrayList<>();

    private void initGenderSpinner() {
        genderList.add("Female");
        genderList.add("Male");
        initSpinner(spnrGender, genderList);
    }

    private void initSpinner(final Spinner spinner, final ArrayList<String> items) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(NewRegistrationActivity.this,
                R.layout.row_spinner, items);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.iv_password_toggle)
    public void setPasswordToggle() {
        passwordToggle = !passwordToggle;

        ivPasswordToggle.setImageDrawable(ContextCompat.getDrawable(this, passwordToggle
                ? R.drawable.ic_visibilityon_blue_48dp : R.drawable.ic_visibilityoff_blue_48dp));

        etPassword.setInputType(passwordToggle? InputType.TYPE_CLASS_TEXT
                :  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        etPassword.setSelection(etPassword.getText().length());
    }

    @OnClick(R.id.iv_confirm_password_toggle)
    public void setConfirmPasswordToggle() {
        confirmPasswordToggle = !confirmPasswordToggle;

        ivConfirmPasswordToggle.setImageDrawable(ContextCompat.getDrawable(this, confirmPasswordToggle
                ? R.drawable.ic_visibilityon_blue_48dp : R.drawable.ic_visibilityoff_blue_48dp));

        etConfirmPassword.setInputType(confirmPasswordToggle? InputType.TYPE_CLASS_TEXT
                :  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        etConfirmPassword.setSelection(etConfirmPassword.getText().length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    boolean firstSelection;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (firstSelection) {
            tvSpinner.setText(genderList.get(i));
            tvSpinner.setTextColor(ContextCompat.getColor(this, R.color.status_bar_bg));
            validatePersonalInformation();
        } else if (!firstSelection) {
            firstSelection = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        showCustomProgress("Submitting Information...");
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        showConfirmDialog("", getString(R.string.registration_success_title),
                getString(R.string.registration_success_content),
                getString(R.string.registration_success_confirm), null, new OnConfirmDialogListener() {
                    @Override
                    public void onConfirmed(String action) {
                        cancelSignUp();
                    }

                    @Override
                    public void onCancelled(String action) {

                    }
                });
    }

    private void emailTaken() {
        isEmailValid = false;
        tvEmailValidation.setText(R.string.alert_email_taken);
        rlEmailValidation.setVisibility(View.VISIBLE);
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            if (apiError.getMessage().equals(getString(R.string.email_taken_error))) {
                emailTaken();
            } else {
                showSnackbar(ivNext, apiError.getMessage());
            }
        } else {
            showSnackbar(ivNext, t.getMessage());
        }
    }
}
