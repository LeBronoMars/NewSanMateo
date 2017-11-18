package sanmateo.com.profileapp.waterlevel.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DefaultWaterLevelSaver implements RoomWaterLevelSaver {

    private WaterLevelDao waterLevelDao;

    @Inject
    public DefaultWaterLevelSaver(WaterLevelDao waterLevelDao) {
        this.waterLevelDao = waterLevelDao;
    }

    @Override
    public Completable saveWaterLevel(List<WaterLevel> waterLevel) {
        return Completable.fromCallable(() -> {
                waterLevelDao.insertAll(waterLevel);
                return Completable.complete();
            });
    }
}
