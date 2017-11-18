package sanmateo.com.profileapp.waterlevel.usecase;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface WaterLevelLoader {

    Maybe<List<WaterLevel>> loadWaterLevels(String area);
}
