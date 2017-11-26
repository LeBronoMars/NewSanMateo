package sanmateo.com.profileapp.waterlevel.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelLoader;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelSaver;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteLoader;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;

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
    public Single<List<WaterLevel>> loadWaterLevels(String area) {
        // load water levels from API
        return waterLevelRemoteLoader.waterLevels(area)
                                     // translate each WaterLevelDto to WaterLevel
                                     .compose(new DtoToWaterLevelMapper())
                                     // save to local
                                     .flatMapSingle(this::saveToLocal)
                                     // save each fetched WaterLevel to local if not existing
                                     .onErrorResumeNext(loadFromLocal(area))
                                     .toSortedList((w1, w2)
                                                       -> w2.createdAt.compareTo(w1.createdAt));
    }

    @Override
    public Completable saveWaterLevelToLocal(List<WaterLevel> waterLevels) {
        return roomWaterLevelSaver.saveWaterLevel(waterLevels);
    }

    public Observable<WaterLevel> loadFromLocal(String area) {
        return roomWaterLevelLoader.loadWaterLevel(area)
                                   .flatMapObservable(Observable::fromIterable);
    }

    private Single<WaterLevel> saveToLocal(WaterLevel waterLevel) {
        return roomWaterLevelSaver.saveWaterLevel(waterLevel)
                                  .andThen(Single.just(waterLevel));
    }
}
