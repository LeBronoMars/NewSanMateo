package sanmateo.com.profileapp.waterlevel.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.user.local.NoQueryResultException;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

public class DefaultRoomWaterLevelLoader implements RoomWaterLevelLoader {

    private WaterLevelDao waterLevelDao;

    @Inject
    public DefaultRoomWaterLevelLoader(WaterLevelDao waterLevelDao) {
        this.waterLevelDao = waterLevelDao;
    }

    @Override
    public Maybe<List<WaterLevel>> loadWaterLevel(String area) {
        return waterLevelDao.findByArea(area)
                            .switchIfEmpty(Maybe.error(NoQueryResultException::new));
    }
}
