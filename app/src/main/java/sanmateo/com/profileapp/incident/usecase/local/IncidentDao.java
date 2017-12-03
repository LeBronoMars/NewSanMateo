package sanmateo.com.profileapp.incident.usecase.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.incident.usecase.Incident;

/**
 * Created by rsbulanon on 02/12/2017.
 */

@Dao
public interface IncidentDao {

    @Query("SELECT COUNT(*) FROM Incident")
    Single<Long> count();

    @Delete
    void delete(Incident incident);

    @Query("SELECT * FROM Incident ORDER BY incidentDateUpdated DESC")
    Maybe<List<Incident>> findAll();

    @Query("SELECT * FROM Incident where incidentType = :incidentType ORDER BY incidentDateUpdated DESC")
    Maybe<List<Incident>> findByIncidentType(String incidentType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Incident incident);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Incident> incidents);
}
