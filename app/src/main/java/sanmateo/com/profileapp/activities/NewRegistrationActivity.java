package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.customviews.CustomSpinner;

/**
 * Created by USER on 8/6/2017.
 */

public class NewRegistrationActivity extends BaseActivity implements OnItemSelectedListener{

    @BindView(R.id.ll_account_information)
    LinearLayout llAccountInformation;

    @BindView(R.id.ll_personal_information)
    LinearLayout llPersonalInformation;

    @BindView(R.id.iv_next)
    ImageView ivNext;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.tv_steps)
    TextView tvSteps;

    @BindView(R.id.tv_spinner)
    TextView tvSpinner;

    @BindView(R.id.spnr_gender)
    CustomSpinner spnrGender;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup);
        unbinder = ButterKnife.bind(this);
        initGenderSpinner();

        addPersonalInfoValidation();
    }

    @OnClick(R.id.tv_spinner)
    public void showSpinner() {
        spnrGender.performClick();
    }

    @OnClick(R.id.iv_close)
    public void cancelSignUp() {
        onBackPressed();
    }

    boolean isPersonalValid;

    @BindView(R.id.et_first_name)
    TextInputEditText etFirstName;

    @BindView(R.id.et_last_name)
    TextInputEditText etLastName;

    @BindView(R.id.et_contact_no)
    TextInputEditText etContactNo;

    @BindView(R.id.et_address)
    TextInputEditText etAddress;

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
        }
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
}
