package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.FacebookFeedsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.com.profileapp.models.others.FacebookFeeds;

/**
 * Created by rsbulanon on 8/29/16.
 */
public class SocialMediaActivity extends BaseActivity implements GraphRequest.Callback {
    @BindView(R.id.rv_feeds) RecyclerView rv_feeds;
    private CallbackManager callbackManager;
    private ArrayList<FacebookFeeds> feeds = new ArrayList<>();
    private GraphRequest graphRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        ButterKnife.bind(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_feeds.setAdapter(new FacebookFeedsAdapter(feeds));
        rv_feeds.setLayoutManager(linearLayoutManager);

        rv_feeds.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (graphRequest != null) {
                    showCustomProgress("Fetching feeds from San Mateo's official facebook page, Please wait...");
                    graphRequest.setCallback(SocialMediaActivity.this);
                    graphRequest.executeAsync();
                }
            }
        });


        callbackManager = CallbackManager.Factory.create();
        setToolbarTitle("Social Media");

        if (AccessToken.getCurrentAccessToken() == null || AccessToken.getCurrentAccessToken().isExpired()) {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    LogHelper.log("fb","on login success --> " + loginResult.getAccessToken());
                    getFeeds();
                }

                @Override
                public void onCancel() {
                    LogHelper.log("fb","on login cancelled");
                    onBackPressed();
                }

                @Override
                public void onError(FacebookException error) {
                    LogHelper.log("fb","on fb error --> " + error.getMessage());
                }
            });
            LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        } else {
            getFeeds();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void getFeeds() {
        showCustomProgress("Fetching feeds from San Mateo's official facebook page, Please wait...");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/sanmateolgu/feed",
                            null, HttpMethod.GET, SocialMediaActivity.this).executeAsync();
    }

    @Override
    public void onCompleted(GraphResponse response) {
        dismissCustomProgress();
        try {
            final JSONObject json = new JSONObject(response.getRawResponse());
            final JSONArray jsonArray = json.getJSONArray("data");
            graphRequest = null;
            if (json.has("paging")) {
                final JSONObject paging = json.getJSONObject("paging");
                if (paging.has("next")) {
                    graphRequest = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
                }
            }
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                final JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.has("message")) {
                    feeds.add(new FacebookFeeds(obj.getString("message"),
                            obj.getString("created_time")));
                }
            }
            rv_feeds.getAdapter().notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
