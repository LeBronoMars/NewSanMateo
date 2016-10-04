package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class RegistrationActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.et_first_name) EditText et_first_name;
    @BindView(R.id.et_last_name) EditText et_last_name;
    @BindView(R.id.et_contact_no) EditText et_contact_no;
    @BindView(R.id.et_address) EditText et_address;
    @BindView(R.id.et_email) EditText et_email;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.et_confirm_password) EditText et_confirm_password;
    @BindView(R.id.spnr_gender) Spinner spnr_gender;
    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        apiRequestHelper = new ApiRequestHelper(this);
        initGenderSpinner();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    @OnClick({R.id.ivBack, R.id.btn_create_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                LogHelper.log("reg", "on back pressed");
                onBackPressed();
                break;
            case R.id.btn_create_account:
                LogHelper.log("reg", "must create account");
                createAccount();
                break;
        }
    }

    private void createAccount() {
        if (!isNetworkAvailable()) {
            showToast(AppConstants.WARN_CONNECTION);
        } else if (et_first_name.getText().toString().isEmpty()) {
            setError(et_first_name, AppConstants.WARN_FIELD_REQUIRED);
        } else if (et_last_name.getText().toString().isEmpty()) {
            setError(et_last_name, AppConstants.WARN_FIELD_REQUIRED);
        } else if (et_contact_no.getText().toString().isEmpty()) {
            setError(et_contact_no, AppConstants.WARN_FIELD_REQUIRED);
        } else if (et_address.getText().toString().isEmpty()) {
            setError(et_address, AppConstants.WARN_FIELD_REQUIRED);
        } else if (!isValidEmail(et_email.getText().toString())) {
            setError(et_email, AppConstants.WARN_INVALID_EMAIL_FORMAT);
        } else if (et_password.getText().toString().isEmpty()) {
            setError(et_password, AppConstants.WARN_FIELD_REQUIRED);
        } else if (!et_password.getText().toString().equals(et_confirm_password.getText().toString())) {
            setError(et_confirm_password, AppConstants.WARN_PASSWORD_NOT_MATCH);
        } else {
            final String firstName = et_first_name.getText().toString();
            final String lastName = et_last_name.getText().toString();
            final String contactNo = et_contact_no.getText().toString();
            final String address = et_address.getText().toString();
            final String email = et_email.getText().toString();
            final String password = et_password.getText().toString();
            final String gender = spnr_gender.getSelectedItem().toString();
            final String userLevel = "Regular User";
            if (gender.equals("Select Gender")) {
                showToast(AppConstants.WARN_SELECT_GENDER);
            } else {
                apiRequestHelper.createUser(firstName, lastName, contactNo, gender, email, address,
                        userLevel, password);
            }
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.POST_REGISTER)) {
            showCustomProgress("Creating your account, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.POST_REGISTER)) {
            showToast("Congratulation! You have successfully created your account!");
            final AuthResponse authResponse = (AuthResponse) result;
            CurrentUserSingleton.newInstance().setCurrentUser(authResponse);
            startActivity(new Intent(this, HomeActivity.class));
            animateToLeft(RegistrationActivity.this);
            finish();
        }
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        if (t instanceof HttpException) {
            if (action.equals(ApiAction.POST_AUTH)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog("","Registration Failed", apiError.getMessage(),"Ok","",null);
            }
        }
    }

    private void initGenderSpinner() {
        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Select Gender");
        genderList.add("Male");
        genderList.add("Female");
        initSpinner(spnr_gender, genderList);
    }

    private void initSpinner(final Spinner spinner, final ArrayList<String> items) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(RegistrationActivity.this,
                R.layout.row_spinner, items);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinner.setAdapter(adapter);
    }
}
