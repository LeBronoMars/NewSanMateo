package sanmateo.com.profileapp.waterlevel.local;

import java.util.List;

import io.reactivex.Completable;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface RoomWaterLevelSaver {

    Completable saveWaterLevel(WaterLevel waterLevel);

    Completable saveWaterLevel(List<WaterLevel> waterLevel);
}
