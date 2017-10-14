package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
 * Created by USER on 10/14/2017.
 */

public class FileIncidentActivity extends BaseActivity implements OnItemSelectedListener{

    private Unbinder unbinder;
    private ArrayList<String> incidentFilingList = new ArrayList<>();

    @BindView(R.id.ll_action_bar)
    LinearLayout llActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.spinner_type)
    Spinner spinnerType;

    @BindView(R.id.tv_filing_type)
    TextView tvFilingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_incident);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);

        initFilingIncidentType();
    }

    private void initFilingIncidentType() {
        incidentFilingList.add(getString(R.string.online_mode));
        incidentFilingList.add(getString(R.string.sms_mode));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spinner,
                incidentFilingList);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinnerType.setOnItemSelectedListener(this);
        spinnerType.setAdapter(adapter);
    }

    @OnClick(R.id.rl_filing_type)
    public void clickSelection(){
        spinnerType.performClick();
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
