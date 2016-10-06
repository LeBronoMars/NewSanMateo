package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.OfficialsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.response.Official;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.OfficialsSingleton;

/**
 * Created by ctmanalo on 10/6/16.
 */

public class OfficialsActivity extends BaseActivity implements OnApiRequestListener{

    @BindView(R.id.rv_officials) RecyclerView rvOfficials;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private OfficialsSingleton officialsSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officials);
        ButterKnife.bind(this);
        setToolbarTitle("Officials");
        initResources();
        initOfficialListing();
    }

    private void initResources() {
        currentUserSingleton = CurrentUserSingleton.getInstance();
        officialsSingleton = OfficialsSingleton.getInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        if (officialsSingleton.getListOfficials().isEmpty()) {
            if (isNetworkAvailable()) {
                apiRequestHelper.getOfficials(token);
            } else {
                showConfirmDialog("", "Officials", AppConstants.WARN_CONNECTION, "Close", "",
                        new OnConfirmDialogListener() {
                            @Override
                            public void onConfirmed(String action) {
                                onBackPressed();
                            }

                            @Override
                            public void onCancelled(String action) {

                            }
                        });
            }
        }
    }

    private void initOfficialListing() {
        rvOfficials.setAdapter(new OfficialsAdapter(officialsSingleton.getListOfficials()));
        rvOfficials.setLayoutManager(new LinearLayoutManager(this));
        rvOfficials.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.GET_OFFICIALS)) {
            showCustomProgress("Fetching list of officials, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.GET_OFFICIALS)) {
            final List<Official> officials = (List<Official>)result;
            officialsSingleton.getListOfficials().clear();
            officialsSingleton.getListOfficials().addAll(officials);
        }
        rvOfficials.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
    }
}
