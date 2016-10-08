package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.AnnouncementsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiErrorHelper;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.Announcement;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.singletons.AnnouncementsSingleton;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class PublicAnnouncementsActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rv_announcements)
    RecyclerView rv_announcements;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;
    private AnnouncementsSingleton announcementsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_announcements);
        ButterKnife.bind(this);
        setToolbarTitle("Public Announcements");
        announcementsSingleton = AnnouncementsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.getInstance();
        apiRequestHelper = new ApiRequestHelper(this);
        token = currentUserSingleton.getCurrentUser().getToken();

        if (PrefsHelper.getBoolean(this, "refresh_announcements") && announcementsSingleton.getAnnouncements().size() > 0) {
            apiRequestHelper.getLatestAnnouncements(token, announcementsSingleton.getAnnouncements().get(0).getId());
        } else if (announcementsSingleton.getAnnouncements().size() == 0) {
            apiRequestHelper.getAnnouncements(token, 0, 10);
        }
        initAnnouncements();

        if (currentUserSingleton.getCurrentUser().getUserLevel().equals("Regular User")) {
            btnAdd.setVisibility(View.INVISIBLE);
        }
        seen();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogHelper.log("intent", "on new intent called");
        seen();
    }

    private void initAnnouncements() {
        final AnnouncementsAdapter adapter = new AnnouncementsAdapter(this, announcementsSingleton.getAnnouncements());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_announcements.setAdapter(adapter);
        rv_announcements.setLayoutManager(linearLayoutManager);
        rv_announcements.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                apiRequestHelper.getAnnouncements(token, announcementsSingleton.getAnnouncements().size(), 10);
            }
        });
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.GET_ANNOUNCEMENTS)) {
            showCustomProgress("Getting announcements, Please wait...");
        } else if (action.equals(ApiAction.GET_LATEST_ANNOUNCEMENTS)) {
            showCustomProgress("Fetching latest announcements, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        if (action.equals(ApiAction.GET_ANNOUNCEMENTS)) {
            final ArrayList<Announcement> announcements = (ArrayList<Announcement>) result;
            announcementsSingleton.getAnnouncements().addAll(announcements);
        } else if (action.equals(ApiAction.GET_LATEST_ANNOUNCEMENTS)) {
            final ArrayList<Announcement> announcements = (ArrayList<Announcement>) result;
            announcementsSingleton.getAnnouncements().addAll(0, announcements);
            PrefsHelper.setBoolean(this, "refresh_announcements", false);
        }
        rv_announcements.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err", "error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(ApiAction.POST_AUTH)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action.name(), "Login Failed", apiError.getMessage(), "Close", "", null);
            }
        }
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String, Object> map) {
        runOnUiThread(() -> {
            if (map.containsKey("data")) {
                try {
                    final JSONObject json = new JSONObject(map.get("data").toString());
                    if (json.has("action")) {
                        final String action = json.getString("action");

                        /** new incident notification */
                        if (action.equals("announcements")) {
                            if (announcementsSingleton.getAnnouncements().size() == 0) {
                                apiRequestHelper.getAnnouncements(token, 0, 10);
                            } else {
                                seen();
                                apiRequestHelper.getLatestAnnouncements(token, announcementsSingleton.getAnnouncements().get(0).getId());
                            }
                        }
                        rv_announcements.getAdapter().notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void seen() {
        if (PrefsHelper.getBoolean(this, "has_notifications")) {
            PrefsHelper.setBoolean(this, "has_notifications", false);
        }
    }
}
