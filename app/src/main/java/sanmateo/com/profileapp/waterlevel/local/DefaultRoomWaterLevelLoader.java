package sanmateo.com.profileapp.waterlevel.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

public class DefaultRoomWaterLevelLoader implements RoomWaterLevelLoader {

    private WaterLevelDao waterLevelDao;

    @Inject
    public DefaultRoomWaterLevelLoader(WaterLevelDao waterLevelDao) {
        this.waterLevelDao = waterLevelDao;
    }

    @Override
    public Single<List<WaterLevel>> loadWaterLevel(String area) {
        return waterLevelDao.findByArea(area);
    }
}
