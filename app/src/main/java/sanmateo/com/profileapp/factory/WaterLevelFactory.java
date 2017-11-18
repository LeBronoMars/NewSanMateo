package sanmateo.com.profileapp.factory;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;

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
        waterLevelDto.id = FACTORY.getNumberBetween(1, 1000);
        waterLevelDto.updatedAt = FACTORY.getBirthDate().toString();
        waterLevelDto.waterLevel = FACTORY.getNumberBetween(1, 40);
        return waterLevelDto;
    }

    public static ArrayList<WaterLevelDto> dtos() {
        ArrayList<WaterLevelDto> waterLevelDtos = new ArrayList<>();
        for (int i = 0; i < FACTORY.getNumberBetween(0, 20); i++) {
            waterLevelDtos.add(dto());
        }
        return waterLevelDtos;
    }

    public static ArrayList<WaterLevelDto> dtos(String area) {
        ArrayList<WaterLevelDto> waterLevelDtos = new ArrayList<>();
        for (int i = 0; i < FACTORY.getNumberBetween(0, 20); i++) {
            WaterLevelDto waterLevelDto = dto();
            waterLevelDto.area = area;
            waterLevelDtos.add(waterLevelDto);
        }
        return waterLevelDtos;
    }
}
