package sanmateo.com.profileapp.waterlevel.usecase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface WaterLevelLoader {

    Single<List<WaterLevel>> loadWaterLevels(String area);

    Completable saveWaterLevelToLocal(List<WaterLevel> waterLevels);
}
