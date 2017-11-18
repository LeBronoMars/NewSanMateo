package sanmateo.com.profileapp.waterlevel.local;

import java.util.List;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface RoomWaterLevelLoader {

    Maybe<List<WaterLevel>> loadWaterLevel(String area);
}
