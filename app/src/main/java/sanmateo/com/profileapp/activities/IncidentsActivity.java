package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.IncidentsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.AmazonS3Helper;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnS3UploadListener;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.IncidentsSingleton;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsActivity extends BaseActivity implements OnApiRequestListener, OnS3UploadListener {

    @BindView(R.id.rv_incidents)
    RecyclerView rv_incidents;
    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;
    private ApiRequestHelper apiRequestHelper;
    private IncidentsSingleton incidentsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private Bundle bundle = new Bundle();
    private AmazonS3Helper amazonS3Helper;
    private int filesToUploadCtr = 0;
    private ArrayList<File> filesToUpload = new ArrayList<>();
    private StringBuilder uploadedFilesUrl = new StringBuilder();
    private CallbackManager shareCallBackManager;
    private String status = "active";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_incidents);
        ButterKnife.bind(this);
        initAmazonS3Helper(this);
        amazonS3Helper = new AmazonS3Helper(this);
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.getInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        shareCallBackManager = CallbackManager.Factory.create();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(this,"refresh_incidents") && incidentsSingleton.getIncidents(status).size() > 0) {
            apiRequestHelper.getLatestIncidents(token,incidentsSingleton.getIncidents(status).get(0).getIncidentId());
        } else if (incidentsSingleton.getIncidents(status).size() == 0) {
            //if incidents is empty, fetch it from api
            LogHelper.log("api","must get all");
            apiRequestHelper.getAllIncidents(token,0,null,status);
        }
        initIncidents();
    }

    @Override
    public void onApiRequestBegin(final ApiAction action) {
        LogHelper.log("api","begin --> " + action);
        if (action.equals(ApiAction.GET_INCIDENTS)) {
            showCustomProgress("Fetching all incident reports, Please wait...");
        } else if (action.equals(ApiAction.GET_LATEST_INCIDENTS)) {
            showCustomProgress("Fetching latest incident reports, Please wait...");
        } else if (action.equals(ApiAction.POST_INCIDENT_REPORT)) {
            showCustomProgress("Filing your incident report, Please wait...");
        } else if (action.equals(ApiAction.POST_MALICIOUS_REPORT)) {
            showCustomProgress("Submitting your report, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(final ApiAction action, final Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.GET_INCIDENTS) || action.equals(ApiAction.GET_LATEST_INCIDENTS)) {
            final ArrayList<Incident> incidents = (ArrayList<Incident>)result;
            LogHelper.log("api","success size --> " + incidents);
            incidentsSingleton.getIncidents(status).addAll(0,incidents);
            rv_incidents.getAdapter().notifyDataSetChanged();
            if (action.equals(ApiAction.GET_LATEST_INCIDENTS)) {
                PrefsHelper.setBoolean(this,"refresh_incidents",false);
            }
        } else if (action.equals(ApiAction.POST_MALICIOUS_REPORT)) {
            showConfirmDialog("","Malicious Incident Report","You have successfully filed a complaint about this " +
                    "incident report.","Close","",null);
        } else if (action.equals(ApiAction.POST_INCIDENT_REPORT)) {
            showConfirmDialog("","Incident Report","You have successfully filed an incident report" +
                    ". Admins will review it first for publication.","Close","",null);
        }
    }

    @Override
    public void onApiRequestFailed(final ApiAction action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            showConfirmDialog(action,"Failed", apiError.getMessage(),"Close","",null);
        }
    }

    private void initIncidents() {
        final IncidentsAdapter adapter = new IncidentsAdapter(this,incidentsSingleton.getIncidents(status));
        adapter.setOnShareAndReportListener(new IncidentsAdapter.OnShareAndReportListener() {
            @Override
            public void onShare(int position) {
                LogHelper.log("fb","must share --> " + position);
                if (isNetworkAvailable()) {
                    final Incident incident = incidentsSingleton.getIncidents(status).get(position);
                    final String imageUrl = incident.getImages().contains("###") ?
                            incident.getImages().split("###")[0] : "";
                    final ShareDialog shareDialog = new ShareDialog(IncidentsActivity.this);
                    shareDialog.registerCallback(shareCallBackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            LogHelper.log("fb","Successfully shared in facebook ---> " + result.getPostId());
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {
                            LogHelper.log("fb","Unable to share with error --> " + error.getMessage());
                        }
                    });
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        final ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle(incident.getIncidentDescription())
                                .setImageUrl(Uri.parse(imageUrl.isEmpty() ? AppConstants.SAN_MATEO_LOGO : imageUrl))
                                .setContentDescription(incident.getIncidentAddress())
                                .build();
                        shareDialog.show(linkContent, AppConstants.IS_FACEBOOK_APP_INSTALLED
                                ? ShareDialog.Mode.NATIVE : ShareDialog.Mode.FEED);
                    }
                } else {
                    showSnackbar(btnAdd, AppConstants.WARN_CONNECTION);
                }
            }

            @Override
            public void onReport(int position) {
                final Incident incident = incidentsSingleton.getIncidents(status).get(position);
                final ReportIncidentReportDialogFragment fragment = ReportIncidentReportDialogFragment.newInstance();
                fragment.setOnReportIncidentListener(new ReportIncidentReportDialogFragment.OnReportIncidentListener() {
                    @Override
                    public void onReportIncident(String remarks) {
                        fragment.dismiss();
                        apiRequestHelper.reportMaliciousIncidentReport(token,incident.getIncidentId(),
                                incident.getReporterId(),currentUserSingleton.getCurrentUser().getUserId(),
                                remarks);
                    }

                    @Override
                    public void onReportCancel() {
                        fragment.dismiss();
                    }
                });
                fragment.show(getFragmentManager(),"report incident");
            }
        });
        rv_incidents.setLayoutManager(new LinearLayoutManager(this));
        rv_incidents.setAdapter(adapter);
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        runOnUiThread(() -> {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    final String action = json.getString("action");

                    /** new incident notification */
                    if (action.equals("new incident")) {
                        LogHelper.log("api","must fetch latest incident reports");
                        if (incidentsSingleton.getIncidents(status).size() == 0) {
                            //if incidents is empty, fetch it from api
                            LogHelper.log("api","must get all");
                            apiRequestHelper.getAllIncidents(token,0,null,"active");
                        } else {
                            apiRequestHelper.getLatestIncidents(token,incidentsSingleton
                                    .getIncidents(status).get(0).getIncidentId());
                        }
                    }
                    rv_incidents.getAdapter().notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @OnClick(R.id.btn_add)
    public void fileIncidentReport() {
        final FileIncidentReportDialogFragment fragment = FileIncidentReportDialogFragment.newInstance();
        fragment.setOnFileIncidentReportListener(new FileIncidentReportDialogFragment.OnFileIncidentReportListener() {
            @Override
            public void onFileReport(String incidentDescription, String incidentLocation,
                                     String incidentType, final ArrayList<File> files) {
                fragment.dismiss();
                if (files.size() > 0) {
                    bundle.putString("incidentDescription",incidentDescription);
                    bundle.putString("incidentLocation",incidentLocation);
                    bundle.putString("incidentType",incidentType);
                    filesToUpload.clear();
                    filesToUpload.addAll(files);
                    filesToUploadCtr = 0;
                    uploadedFilesUrl.setLength(0);
                    uploadImage();
                } else {
                    apiRequestHelper.fileIncidentReport(token,incidentLocation,
                            incidentDescription,incidentType, 1,1,
                            currentUserSingleton.getCurrentUser().getUserId(), "");
                }
            }

            @Override
            public void onCancelReport(ArrayList<File> filesToUpload) {
                if (filesToUpload.size() > 0) {
                    for (File f : filesToUpload) {
                        f.delete();
                    }
                }
                fragment.dismiss();
            }
        });
        fragment.setCancelable(false);
        fragment.show(getFragmentManager(),"file incident report");
    }

    private void uploadImage() {
        if (filesToUploadCtr < filesToUpload.size()) {
            uploadImageToS3(AppConstants.BUCKET_INCIDENTS,filesToUpload.get(filesToUploadCtr),
                    filesToUploadCtr+1,filesToUpload.size());
        } else {
            for (File f : filesToUpload) {
                f.delete();
            }
            dismissCustomProgress();
            LogHelper.log("s3","uploading of all files successfully finished!");
            LogHelper.log("s3","FINAL URL --->  " + uploadedFilesUrl.toString());
            apiRequestHelper.fileIncidentReport(token,bundle.getString("incidentLocation"),
                    bundle.getString("incidentDescription"),bundle.getString("incidentType"),
                    1,1,currentUserSingleton.getCurrentUser().getUserId(),
                    uploadedFilesUrl.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        shareCallBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_INCIDENTS)) {
            filesToUploadCtr++;
            uploadedFilesUrl.append(imageUrl+"###");
        }
        uploadImage();
    }
}
