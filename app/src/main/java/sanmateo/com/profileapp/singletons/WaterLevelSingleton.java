package sanmateo.com.profileapp.singletons;

import java.util.ArrayList;

import sanmateo.com.profileapp.models.response.WaterLevel;


/**
 * Created by rsbulanon on 6/28/16.
 */
public class WaterLevelSingleton {

    private ArrayList<WaterLevel> publicMarketArea = new ArrayList<>();
    private ArrayList<WaterLevel> batasanArea = new ArrayList<>();

    private static WaterLevelSingleton WATER_LEVEL = new WaterLevelSingleton();

    private WaterLevelSingleton() {}

    public static WaterLevelSingleton getInstance() {
        return WATER_LEVEL;
    }

    public ArrayList<WaterLevel> getPublicMarketArea() {
        return publicMarketArea;
    }

    public ArrayList<WaterLevel> getBatasanArea() {
        return batasanArea;
    }

    public ArrayList<WaterLevel> getWaterLevel(final String area) {
        if (area.equals("Batasan-San Mateo Bridge")) {
            return batasanArea;
        } else {
            return publicMarketArea;
        }
    }
}
