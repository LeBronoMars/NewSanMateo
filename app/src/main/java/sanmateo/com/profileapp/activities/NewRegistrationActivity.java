package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
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

/**
 * Created by USER on 8/6/2017.
 */

public class NewRegistrationActivity extends BaseActivity {

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

    @BindView(R.id.spnr_gender)
    Spinner spnrGender;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup);
        unbinder = ButterKnife.bind(this);
        initGenderSpinner();
    }

    @OnClick(R.id.iv_close)
    public void cancelSignUp() {
        onBackPressed();
    }

    @OnClick(R.id.iv_next)
    public void next() {
        if (ivBack.getVisibility() == View.GONE) {
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

    private void initGenderSpinner() {
        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Female");
        genderList.add("Male");
        initSpinner(spnrGender, genderList);
    }

    private void initSpinner(final Spinner spinner, final ArrayList<String> items) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(NewRegistrationActivity.this,
                R.layout.row_spinner, items);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
