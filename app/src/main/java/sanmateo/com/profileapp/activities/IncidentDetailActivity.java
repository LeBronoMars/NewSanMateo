package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by USER on 10/13/2017.
 */

public class IncidentDetailActivity extends BaseActivity implements OnApiRequestListener {

    private Unbinder unbinder;
    private Incident incident;

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

    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_detail);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor(llActionBar, statusBar);

        shareCallBackManager = CallbackManager.Factory.create();

        int selectedIncidentId = getIntent().getIntExtra("selectedId", 0);

        if (selectedIncidentId > 0) {
            if (isNetworkAvailable()) {
                apiRequestHelper = new ApiRequestHelper(this);
                apiRequestHelper.getIncidentById(CurrentUserSingleton.getInstance().getCurrentUser().getToken(),
                                                 selectedIncidentId);
            } else {
                showToast(AppConstants.WARN_CONNECTION);
                finish();
            }
        } else {
            incident = getIntent().getParcelableExtra("incident");
            initDetails();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shareCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initDetails() {

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

    private CallbackManager shareCallBackManager;

    @OnClick(R.id.iv_share)
    public void share() {
        if (isNetworkAvailable()) {

            final String imageUrl = incident.getImages();
            final ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(shareCallBackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                }
            });

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                final ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(incident.getIncidentType())
                        .setImageUrl(Uri.parse(imageUrl.isEmpty() ? AppConstants.SAN_MATEO_LOGO : imageUrl))
                        .setContentUrl(Uri.parse("www.google.com"))
                        .setContentDescription(incident.getIncidentAddress())
                        .build();
                shareDialog.show(linkContent, AppConstants.IS_FACEBOOK_APP_INSTALLED
                        ? ShareDialog.Mode.NATIVE : ShareDialog.Mode.FEED);
            }
        } else {
            showSnackbar(llContainer, AppConstants.WARN_CONNECTION);
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
        if (action.equals(ApiAction.GET_INCIDENT_BY_ID)) {
            showCustomProgress("Loading incident details, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();

        if (action.equals(ApiAction.GET_INCIDENT_BY_ID)) {
            incident = (Incident) result;
            initDetails();
        }
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();

    }
}
