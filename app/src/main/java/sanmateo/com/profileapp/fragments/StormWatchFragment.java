package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;

/**
 * Created by USER on 10/20/2017.
 */

public class StormWatchFragment extends Fragment {

    @BindView(R.id.tv_storm_report)
    TextView tvStormReport;

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

        initViews();

        return view;
    }

    private void initViews() {
        tvStormReport.setText("\n" +
                "At 10:00 AM today, the eye of Typhoon \"PAOLO\" was located based on all available data at 945 km East of Infanta, Quezon (15.5 °N, 130.4 °E)\n" +
                "\n" +
                "Maximum sustained winds of 120 kph near the center and gustiness of up to 145 kph\n" +
                "\n" +
                "Forecast to move North Northwest at 15 kph\n" +
                "\n" +
                "- 24 Hour(Tomorrow morning): 845 km East of Tuguegarao City, Cagayan(18.7°N, 129.6°E)\n" +
                "- 48 Hour(Saturday morning):830 km East of Basco, Batanes(21.3°N, 129.9°E)\n" +
                "- 72 Hour(Sunday morning): 1,045 km East Northeast of Basco, Batanes(25.1°N, 130.8°E)\n" +
                "- 96 Hour(Monday morning):1,475 km Northeast of Basco, Batanes(29.1°N, 133.0°E)\n" +
                "- 120 Hour(Tuesday morning):2,370 km Northeast of Basco, Batanes(35.5°N, 139.1°E)");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
