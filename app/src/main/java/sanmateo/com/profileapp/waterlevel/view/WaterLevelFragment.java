package sanmateo.com.profileapp.waterlevel.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import sanmateo.com.profileapp.waterlevel.presenter.WaterLevelPresenter;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 19/11/2017.
 */

public class WaterLevelFragment extends MvpFragment<WaterLevelView, WaterLevelPresenter> implements WaterLevelView {

    @Inject
    WaterLevelPresenter presenter;

    private static final String AREA_BATASAN = "Batasan-San Mateo Bridge";
    private static final String AREA_PUBLIC_MARKET = "San Mateo Public Market";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadWaterLevel(AREA_BATASAN);
        presenter.loadWaterLevel(AREA_PUBLIC_MARKET);
    }

    @Override
    public WaterLevelPresenter createPresenter() {
        return presenter;
    }

    @Override
    public void hideProgress(String area) {

    }

    @Override
    public void showError(String area) {

    }

    @Override
    public void showProgress(String area) {

    }

    @Override
    public void showWaterLevels(String area, List<WaterLevel> waterLevelList) {

    }
}
