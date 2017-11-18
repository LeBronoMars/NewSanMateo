package sanmateo.com.profileapp.waterlevel.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import sanmateo.com.profileapp.models.response.WaterLevel;

@Dao
public interface WaterLevelDao {

    @Query("SELECT * FROM WaterLevel")
    Single<List<WaterLevel>> findAll();

    @Query("SELECT * FROM WaterLevel where area = :area")
    Single<List<WaterLevel>> findByArea(String area);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WaterLevel waterLevel);

    @Delete
    void delete(WaterLevel waterLevel);
}
