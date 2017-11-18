package sanmateo.com.profileapp.factory.waterlevel;

import org.fluttercode.datafactory.impl.DataFactory;

import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;

/**
 * Created by rsbulanon on 18/11/2017.
 */

public class WaterLevelFactory {

    private static final DataFactory FACTORY = new DataFactory();

    public static WaterLevelDto dto() {
        WaterLevelDto waterLevelDto = new WaterLevelDto();
        waterLevelDto.alert = FACTORY.getRandomWord();
        waterLevelDto.area = FACTORY.getRandomWord();
        waterLevelDto.createdAt = FACTORY.getBirthDate().toString();
        waterLevelDto.deletedAt = FACTORY.getBirthDate().toString();
        waterLevelDto.id = FACTORY.getNumber();
        waterLevelDto.updatedAt = FACTORY.getBirthDate().toString();
        waterLevelDto.waterLevel = FACTORY.getNumberBetween(1, 40);
        return waterLevelDto;
    }
}
