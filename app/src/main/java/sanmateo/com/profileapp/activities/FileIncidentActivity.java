package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
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
import sanmateo.com.profileapp.fragments.SelectIncidentFragment;

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

    @BindView(R.id.tv_incident_type)
    TextView tvIncidentType;

    @BindView(R.id.iv_incident_icon)
    ImageView ivIncidentIcon;

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

    @OnClick(R.id.iv_select_incident_type)
    public void selectIncidentType() {
        SelectIncidentFragment selectIncidentFragment = SelectIncidentFragment.newInstance();
        selectIncidentFragment.setOnIncidentTypeListener(index -> {
            activateIncidentType();
            selectIncidentFragment.dismiss();
            setIncidentSelection(index);
        });
        selectIncidentFragment.show(getFragmentManager(), "SELECT_INCIDENT");
    }

    private void activateIncidentType() {
        ivIncidentIcon.setVisibility(View.VISIBLE);
        tvIncidentType.setTextColor(ContextCompat.getColor(this, R.color.read_more_color));
    }

    private void setIncidentSelection(final int index) {
        switch (index) {
            case 0:
                tvIncidentType.setText(getString(R.string.traffic_road));
                break;
            case 1:
                tvIncidentType.setText(getString(R.string.solid_waste));
                break;
            case 2:
                tvIncidentType.setText(getString(R.string.flooding));
                break;
            case 3:
                tvIncidentType.setText(getString(R.string.fire));
                break;
            case 4:
                tvIncidentType.setText(getString(R.string.miscellaneous));
                break;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvFilingType.setText(incidentFilingList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
