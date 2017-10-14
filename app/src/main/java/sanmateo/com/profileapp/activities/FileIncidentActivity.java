package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.IncidentAddImageFragment;
import sanmateo.com.profileapp.fragments.IncidentFilingFragment;

/**
 * Created by USER on 10/14/2017.
 */

public class FileIncidentActivity extends BaseActivity implements OnItemSelectedListener{

    private Unbinder unbinder;
    private ArrayList<String> incidentFilingList = new ArrayList<>();

    @BindView(R.id.ll_action_bar)
    LinearLayout llActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.tv_filing_type)
    TextView tvFilingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_incident);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);
    }

    @OnClick(R.id.rl_filing_type)
    public void clickSelection(){
        IncidentFilingFragment filingFragment = IncidentFilingFragment.newInstance();
        filingFragment.setOnFilingTypeListener(type -> {
            setFilingType(type);
            filingFragment.dismiss();
        });
        filingFragment.show(getFragmentManager(), "FILE_INCIDENT");
    }

    private void setFilingType(final String type) {
        tvFilingType.setText(type);
    }

    @OnClick(R.id.iv_back)
    public void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_capture)
    public void addImage() {
        IncidentAddImageFragment incidentAddImageFragment = IncidentAddImageFragment.newInstance();
        incidentAddImageFragment.setOnAddIncidentImageListener(new IncidentAddImageFragment.OnAddIncidentImageListener() {
            @Override
            public void captureImage() {
                incidentAddImageFragment.dismiss();
                showToast("capture image");
            }

            @Override
            public void addImageFromGallery() {
                incidentAddImageFragment.dismiss();
                showToast("select image");
            }
        });
        incidentAddImageFragment.show(getFragmentManager(), "ADD_IMAGE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvFilingType.setText(incidentFilingList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
