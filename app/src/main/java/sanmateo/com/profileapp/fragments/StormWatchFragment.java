package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.PicassoHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.StormWatch;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;


import static sanmateo.com.profileapp.helpers.PicassoHelper.loadImageFromURL;

/**
 * Created by USER on 10/20/2017.
 */

public class StormWatchFragment extends Fragment implements OnApiRequestListener {

    @BindView(R.id.tv_storm_report)
    TextView tvStormReport;

    @BindView(R.id.pb_load_image)
    ProgressBar pbLoadImage;

    @BindView(R.id.iv_weather_image)
    ImageView ivWeatherImage;

    private ApiRequestHelper apiRequestHelper;

    private Unbinder unbinder;

    public static StormWatchFragment newInstance() {
        final StormWatchFragment fragment = new StormWatchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storm_watch, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiRequestHelper = new ApiRequestHelper(this);
        apiRequestHelper.getStormWatch(CurrentUserSingleton.getInstance().getCurrentUser().getToken());

        return view;
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {

    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        StormWatch stormWatch = ((List<StormWatch>) result).get(0);

        tvStormReport.setText(stormWatch.summary);

        loadImageFromURL(stormWatch.image, ivWeatherImage, pbLoadImage);
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
