package sanmateo.com.profileapp.waterlevel.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelSaver;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteLoader;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DefaultWaterLevelLoader implements WaterLevelLoader {

    private RoomWaterLevelLoader roomWaterLevelLoader;

    private RoomWaterLevelSaver roomWaterLevelSaver;

    private WaterLevelRemoteLoader waterLevelRemoteLoader;

    @Inject
    public DefaultWaterLevelLoader(RoomWaterLevelLoader roomWaterLevelLoader,
                                   RoomWaterLevelSaver roomWaterLevelSaver,
                                   WaterLevelRemoteLoader waterLevelRemoteLoader) {
        this.roomWaterLevelLoader = roomWaterLevelLoader;
        this.roomWaterLevelSaver = roomWaterLevelSaver;
        this.waterLevelRemoteLoader = waterLevelRemoteLoader;
    }

    @Override
    public Maybe<List<WaterLevel>> loadWaterLevels(String area) {
        return roomWaterLevelLoader.loadWaterLevel(area)
                                   .switchIfEmpty(roomWaterLevelLoader.loadWaterLevel(area));
    }

    @Override
    public Completable saveWaterLevelToLocal(List<WaterLevel> waterLevels) {
        return roomWaterLevelSaver.saveWaterLevel(waterLevels);
    }
}
