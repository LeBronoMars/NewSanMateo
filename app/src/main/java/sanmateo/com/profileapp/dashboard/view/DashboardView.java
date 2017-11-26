package sanmateo.com.profileapp.dashboard.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface DashboardView extends MvpView {

    void displayBatasanWaterLevel(WaterLevel waterLevel);

    void displayPublicMarketWaterLevel(WaterLevel waterLevel);
}
