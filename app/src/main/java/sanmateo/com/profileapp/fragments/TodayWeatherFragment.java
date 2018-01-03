package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import sanmateo.com.profileapp.models.response.Weather;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by USER on 10/11/2017.
 */

public class TodayWeatherFragment extends Fragment implements OnApiRequestListener {

    @BindView(R.id.iv_bg_weather)
    ImageView ivBgWeather;

    @BindView(R.id.tv_weather_report_summary)
    TextView tvWeatherReportSummary;

    @BindView(R.id.rl_content)
    RelativeLayout rlContent;

    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;

    @BindView(R.id.iv_weather_icon)
    ImageView ivWeatherIcon;

    @BindView(R.id.tv_heat_index)
    TextView tvHeatIndex;

    @BindView(R.id.tv_temperature)
    TextView tvTemperature;

    @BindView(R.id.tv_humidity)
    TextView tvHumidity;

    @BindView(R.id.tv_uv_index)
    TextView tvUVIndex;

    @BindView(R.id.tv_wind_speed)
    TextView tvWindSpeed;

    @BindView(R.id.tv_cloud_cover)
    TextView tvCloudCover;

    @BindView(R.id.tv_winds_form)
    TextView tvWindsForm;

    @BindView(R.id.tv_wind_gusts)
    TextView tvWindGusts;

    @BindView(R.id.tv_dew_point)
    TextView tvDewPoint;

    @BindView(R.id.tv_pressure)
    TextView tvPressure;

    @BindView(R.id.tv_visibility)
    TextView tvVisibility;

    private Unbinder unbinder;

    private ApiRequestHelper apiRequestHelper;

    public static TodayWeatherFragment newInstance() {
        final TodayWeatherFragment fragment = new TodayWeatherFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiRequestHelper = new ApiRequestHelper(this);
        apiRequestHelper.getWeather(CurrentUserSingleton.getInstance().getCurrentUser().getToken());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {

    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        Weather weather = ((List<Weather>) result).get(0);

        rlProgress.setVisibility(GONE);
        rlContent.setVisibility(VISIBLE);

        PicassoHelper.loadImageFromURL(weather.backgroundImage, ivBgWeather);
        PicassoHelper.loadImageFromURL(weather.weatherIcon, ivWeatherIcon);

        tvWeatherReportSummary.setText(weather.summary.toUpperCase());
        tvHeatIndex.setText("Heat Index: "+ weather.heatIndex+ "\u00B0");
        tvTemperature.setText(""+ weather.temperature +"\u00B0");

        tvHumidity.setText(""+ weather.humidity + "%");
        tvUVIndex.setText(weather.uvIndex);
        tvWindSpeed.setText(""+ weather.windSpeed +" KPH");

        tvCloudCover.setText(""+ weather.cloudCover + "%");
        tvWindsForm.setText(weather.windsFrom);
        tvWindGusts.setText(""+ weather.windGusts +" KPH");

        tvDewPoint.setText(""+ weather.dewPoint +"\u00B0");
        tvPressure.setText(weather.pressure + " MB");
        tvVisibility.setText(weather.visibility + " KM");
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        Log.d("app", "error --> " + t.getMessage());
    }
}
