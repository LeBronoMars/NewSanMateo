package sanmateo.com.profileapp.waterlevel.local;

import java.util.List;

import io.reactivex.Single;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface RoomWaterLevelLoader {

    Single<List<WaterLevel>> loadWaterLevel(String area);
}
