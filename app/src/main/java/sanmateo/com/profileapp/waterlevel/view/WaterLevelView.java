package sanmateo.com.profileapp.waterlevel.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 19/11/2017.
 */

public interface WaterLevelView extends MvpView {

    void hideProgress();

    void showError();

    void showProgress();

    void showWaterLevels(String area, List<WaterLevel> waterLevelList);
}
