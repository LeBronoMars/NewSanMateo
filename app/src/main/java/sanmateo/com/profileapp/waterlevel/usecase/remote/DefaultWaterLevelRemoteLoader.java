package sanmateo.com.profileapp.waterlevel.usecase.remote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
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
    public Single<List<WaterLevelDto>> waterLevels(String area) {
        return remoteService.waterLevels(area)
                            .flatMap(response -> Single.just(response.body()));
    }
}
