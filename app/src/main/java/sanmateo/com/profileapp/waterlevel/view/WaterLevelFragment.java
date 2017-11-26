package sanmateo.com.profileapp.waterlevel.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.user.login.model.User;
import sanmateo.com.profileapp.waterlevel.presenter.WaterLevelPresenter;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.String.format;

/**
 * Created by rsbulanon on 19/11/2017.
 */

public class WaterLevelFragment extends MvpFragment<WaterLevelView, WaterLevelPresenter>
    implements WaterLevelView {

    @BindString(R.string.water_level_batasan)
    String waterLevelBatasan;

    @BindString(R.string.water_level_public_market)
    String waterLevelPublicMarket;

    @BindString(R.string.water_level_reading)
    String waterLevelReading;

    @BindView(R.id.pb_batasan)
    ProgressBar pbBatasan;

    @BindView(R.id.pb_public_market)
    ProgressBar pbPublicMarket;

    @BindView(R.id.tv_water_level_label)
    TextView tvWaterLevelLabel;

    @BindView(R.id.tv_water_level_reading_market)
    TextView tvWaterLevelReadingMarket;

    @BindView(R.id.tv_water_level_reading_batasan)
    TextView tvWaterLevelReadingBatasan;

    @Inject
    WaterLevelPresenter presenter;

    @Inject
    User user;

    public static WaterLevelFragment newInstance() {
        return new WaterLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_level, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadWaterLevel(waterLevelBatasan);
        presenter.loadWaterLevel(waterLevelPublicMarket);
    }

    @Override
    public WaterLevelPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void hideProgress(String area) {
        hideView(area);
    }

    @Override
    public void showError(String area) {
        hideView(area);
    }

    @Override
    public void showProgress(String area) {
        if (area.equals(waterLevelBatasan)) {
            pbBatasan.setVisibility(VISIBLE);
            tvWaterLevelReadingBatasan.setVisibility(GONE);
        } else if (area.equals(waterLevelPublicMarket)) {
            pbPublicMarket.setVisibility(VISIBLE);
            tvWaterLevelReadingMarket.setVisibility(GONE);
        }
    }

    @Override
    public void showWaterLevels(String area, List<WaterLevel> waterLevelList) {
        if (!waterLevelList.isEmpty()) {
            WaterLevel latestReading = waterLevelList.get(0);

            if (area.equals(waterLevelBatasan)) {
                tvWaterLevelReadingBatasan.setText(format(waterLevelReading,
                                                          latestReading.waterLevel));
            } else if (area.equals(waterLevelPublicMarket)) {
                tvWaterLevelReadingMarket.setText(format(waterLevelReading,
                                                         latestReading.waterLevel));
            }
        } else {
            if (area.equals(waterLevelBatasan)) {
                tvWaterLevelReadingBatasan.setText(format(waterLevelReading, 0));
            } else if (area.equals(waterLevelPublicMarket)) {
                tvWaterLevelReadingMarket.setText(format(waterLevelReading, 0));
            }
        }
    }

    private void hideView(String area) {
        if (area.equals(waterLevelBatasan)) {
            pbBatasan.setVisibility(GONE);
            tvWaterLevelReadingBatasan.setVisibility(VISIBLE);
        } else if (area.equals(waterLevelPublicMarket)) {
            pbPublicMarket.setVisibility(GONE);
            tvWaterLevelReadingMarket.setVisibility(VISIBLE);
        }
    }
}
