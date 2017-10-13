package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by USER on 10/13/2017.
 */

public class IncidentDetailActivity extends BaseActivity {

    private Unbinder unbinder;

    @BindView(R.id.ll_action_bar)
    LinearLayout llActionBar;

    @BindView(R.id.status_bar)
    View statusBar;

    @BindView(R.id.iv_report_image)
    ImageView ivReportImage;

    @BindView(R.id.tv_incident_title)
    TextView tvIncidentTitle;

    @BindView(R.id.tv_time_location)
    TextView tvTimeLocation;

    @BindView(R.id.tv_submitted_by)
    TextView tvSubmittedBy;

    @BindView(R.id.tv_incident_details)
    TextView tvIncidentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_detail);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);

        initDetails();
    }

    private void initDetails() {
        Incident incident = getIntent().getParcelableExtra("incident");
        if (!incident.getImages().isEmpty()) {
            PicassoSingleton.getInstance().getPicasso().load(incident.getImages())
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .fit()
                    .into(ivReportImage);
        }
        tvIncidentTitle.setText(incident.getIncidentType());
        try {
            final Date dateReported = getDateFormatter().parse(incident.getIncidentDateReported());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            tvTimeLocation.setText(getTime().format(calendar.getTime()) + ", " + incident.getIncidentAddress());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvSubmittedBy.setText("Submitted by: " + incident.getReporterName());
        tvIncidentDetails.setText(incident.getIncidentDescription());
    }

    @OnClick(R.id.iv_back)
    public void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_share)
    public void share() {
        showToast("share");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
