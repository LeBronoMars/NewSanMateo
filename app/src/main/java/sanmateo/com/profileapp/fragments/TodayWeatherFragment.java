package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;

/**
 * Created by USER on 10/11/2017.
 */

public class TodayWeatherFragment extends Fragment {

    @BindView(R.id.iv_bg_weather)
    ImageView ivBgWeather;

    @BindView(R.id.tv_weather_report_summary)
    TextView tvWeatherReportSummary;

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

    public static TodayWeatherFragment newInstance() {
        final TodayWeatherFragment fragment = new TodayWeatherFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();
        return view;
    }

    private void initViews() {
        tvWeatherReportSummary.setText("PARTLY CLOUDY W/ THUNDERSTORMS");
        tvHeatIndex.setText("Heat Index: 32\u00B0");
        ivBgWeather.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_day3));
        ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.day13));
        tvTemperature.setText("29\u00B0");

        tvHumidity.setText("100%");
        tvUVIndex.setText("Moderate");
        tvWindSpeed.setText("20 KPH");

        tvCloudCover.setText("72%");
        tvWindsForm.setText("SW");
        tvWindGusts.setText("20 KPH");

        tvDewPoint.setText("26\u00B0");
        tvPressure.setText("1,008.0 MB");
        tvVisibility.setText("8 KM");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
