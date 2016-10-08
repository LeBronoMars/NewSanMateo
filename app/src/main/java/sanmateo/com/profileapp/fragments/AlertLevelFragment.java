package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.WaterLevelAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.WaterLevel;
import sanmateo.com.profileapp.singletons.BusSingleton;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.WaterLevelSingleton;


/**
 * Created by ctmanalo on 8/28/16.
 */
public class AlertLevelFragment extends Fragment implements OnApiRequestListener {

    @BindView(R.id.rv_listing) RecyclerView rv_listing;
    private BaseActivity activity;
    private WaterLevelSingleton waterLevelSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token, area;
    private ApiRequestHelper apiRequestHelper;
    private ArrayList<WaterLevel> waterLevels = new ArrayList<>();

    public static AlertLevelFragment newInstance(String area) {
        AlertLevelFragment fragment = new AlertLevelFragment();
        fragment.area = area;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_alert_level, container, false);
        ButterKnife.bind(this, view);
        initResources();
        initWaterListing();
        return view;
    }

    private void initResources() {
        activity = (BaseActivity) getActivity();
        waterLevelSingleton = WaterLevelSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.getInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        waterLevels.clear();
        waterLevels.addAll(waterLevelSingleton.getWaterLevel(area));

        if (PrefsHelper.getBoolean(activity, "refresh_water_level") ||
            waterLevelSingleton.getWaterLevel(area).size() == 0) {
            apiRequestHelper.getWaterLevelByArea(token, area);
        }
    }

    private void initWaterListing() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        final WaterLevelAdapter adapter = new WaterLevelAdapter(activity, waterLevels);
        rv_listing.setAdapter(adapter);
        rv_listing.setLayoutManager(linearLayoutManager);
        rv_listing.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                apiRequestHelper.getWaterLevels(token,
                        waterLevelSingleton.getWaterLevel(area).size(), 10);
            }
        });
    }


    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.GET_WATER_LEVEL_BY_AREA)) {
            activity.showCustomProgress("Fetching water level notifications, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        activity.dismissCustomProgress();
        if (action.equals(ApiAction.GET_WATER_LEVEL_BY_AREA) ||
                action.equals(ApiAction.GET_LATEST_WATER_LEVEL)) {
            final  ArrayList<WaterLevel> waterLevels = (ArrayList<WaterLevel>) result;
            if (waterLevels.size() > 0) {
                this.waterLevels.addAll(0,waterLevels);
                PrefsHelper.setBoolean(activity, "refresh_water_level", false);
                waterLevelSingleton.getWaterLevel(area).addAll(0,waterLevels);
            }
        }
        rv_listing.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        activity.dismissCustomProgress();
        activity.handleApiException(t);
    }

    @Override
    public void onResume() {
        BusSingleton.getInstance().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        BusSingleton.getInstance().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void handleEventBus(final HashMap<String,Object> map) {
        if (map.containsKey("data")) {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    if (json.getString("action").equals("water level")) {
                        LogHelper.log("ww","action ----> " + json.getString("action"));
                        if (waterLevelSingleton.getWaterLevel(area).size() == 0) {
                            apiRequestHelper.getLatestWaterLevels(token, 0, area);
                        } else {
                            final WaterLevel waterLevel = waterLevelSingleton.getWaterLevel(area)
                                    .get(waterLevelSingleton.getWaterLevel(area).size()-1);
                            apiRequestHelper.getLatestWaterLevels(token, waterLevel.getId(),
                                    area);
                        }
                    }
                }
            } catch (JSONException e) {
                LogHelper.log("ww","error ---> " + e.getMessage());
            }
        }
    }
}
