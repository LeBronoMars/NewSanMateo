package sanmateo.com.profileapp.waterlevel.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteLoader;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DefaultWaterLevelLoader implements WaterLevelLoader {

    private RoomWaterLevelLoader roomWaterLevelLoader;

    private WaterLevelRemoteLoader waterLevelRemoteLoader;

    @Inject
    public DefaultWaterLevelLoader(RoomWaterLevelLoader roomWaterLevelLoader,
                                   WaterLevelRemoteLoader waterLevelRemoteLoader) {
        this.roomWaterLevelLoader = roomWaterLevelLoader;
        this.waterLevelRemoteLoader = waterLevelRemoteLoader;
    }

    @Override
    public Maybe<List<WaterLevel>> loadWaterLevels(String area) {
        return roomWaterLevelLoader.loadWaterLevel(area)
                                   .onErrorResumeNext(roomWaterLevelLoader.loadWaterLevel(area));
    }
}
