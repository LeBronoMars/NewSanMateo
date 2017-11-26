package sanmateo.com.profileapp.waterlevel.usecase.remote;

import io.reactivex.Observable;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public interface WaterLevelRemoteLoader {

    Observable<WaterLevelDto> waterLevels(String area);
}
