package sanmateo.com.profileapp.waterlevel.usecase.remote;

import java.util.List;

import io.reactivex.Single;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface WaterLevelRemoteLoader {

    Single<List<WaterLevelDto>> waterLevels(String area);
}
