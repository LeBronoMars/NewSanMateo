package sanmateo.com.profileapp.waterlevel.usecase.remote;

import org.junit.Test;

import io.reactivex.Observable;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;

/**
 * Created by rsbulanon on 18/11/2017.
 */
public class DtoToWaterLevelMapperTest {

    @Test
    public void apply() {
        Observable.fromIterable(WaterLevelFactory.dtos())
                  .compose(new DtoToWaterLevelMapper())
                  .toList()
                  .test()
                  .assertComplete()
                  .assertComplete();
    }
}