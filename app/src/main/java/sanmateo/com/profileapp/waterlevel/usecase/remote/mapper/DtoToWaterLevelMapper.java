package sanmateo.com.profileapp.waterlevel.usecase.remote.mapper;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class DtoToWaterLevelMapper implements ObservableTransformer<WaterLevelDto, WaterLevel> {

    @Override
    public ObservableSource<WaterLevel> apply(Observable<WaterLevelDto> upstream) {
        return upstream.flatMap(waterLevelDto -> {
            WaterLevel waterLevel = new WaterLevel();
            waterLevel.alert = waterLevelDto.alert;
            waterLevel.area = waterLevelDto.area;
            waterLevel.createdAt = waterLevelDto.createdAt;
            waterLevel.deletedAt = waterLevelDto.deletedAt;
            waterLevel.id = waterLevelDto.id;
            waterLevel.updatedAt = waterLevelDto.updatedAt;
            waterLevel.waterLevel = waterLevelDto.waterLevel;
            return Observable.just(waterLevel);
        });
    }
}
