package sanmateo.com.profileapp.waterlevel.presenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 19/11/2017.
 */
@Module
public interface WaterLevelPresenterModule {

    @Binds
    WaterLevelPresenter bindsWaterLevelPresenter(
        DefaultWaterLevelPresenter defaultWaterLevelPresenter);
}
