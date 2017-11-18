package sanmateo.com.profileapp.waterlevel.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;

@Dao
public interface WaterLevelDao {

    @Query("SELECT * FROM WaterLevel")
    Maybe<List<WaterLevel>> findAll();

    @Query("SELECT * FROM WaterLevel where area = :area ORDER BY createdAt DESC")
    Maybe<List<WaterLevel>> findByArea(String area);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WaterLevel waterLevel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WaterLevel> waterLevels);

    @Delete
    void delete(WaterLevel waterLevel);
}
