package sanmateo.com.profileapp.waterlevel.usecase.remote;

import org.junit.Test;

import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rsbulanon on 18/11/2017.
 */
public class DtoToWaterLevelMapperTest {

    @Test
    public void apply() {
        WaterLevelDto expected = WaterLevelFactory.dto();

        WaterLevel actual = new DtoToWaterLevelMapper()
                                  .apply(expected)
                                  .blockingGet();

        assertThat(actual).isEqualToComparingFieldByField(expected);

    }
}