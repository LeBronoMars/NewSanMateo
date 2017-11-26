package sanmateo.com.profileapp.waterlevel.usecase.remote;

import javax.inject.Inject;

import io.reactivex.Observable;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelRemoteService;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DefaultWaterLevelRemoteLoader implements WaterLevelRemoteLoader {

    private WaterLevelRemoteService remoteService;

    @Inject
    public DefaultWaterLevelRemoteLoader(WaterLevelRemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public Observable<WaterLevelDto> waterLevels(String area) {
        return remoteService.waterLevels(area)
                            .flatMapObservable(response ->
                                                   Observable.fromIterable(response.body()));
    }
}
