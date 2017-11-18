package sanmateo.com.profileapp.waterlevel.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import sanmateo.com.profileapp.waterlevel.view.WaterLevelView;

/**
 * Created by rsbulanon on 19/11/2017.
 */

public interface WaterLevelPresenter extends MvpPresenter<WaterLevelView> {

    void loadWaterLevel(String area);
}
