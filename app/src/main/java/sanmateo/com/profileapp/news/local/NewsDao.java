package sanmateo.com.profileapp.news.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 02/12/2017.
 */

@Dao
public interface NewsDao {

    @Query("SELECT COUNT(*) FROM News")
    Single<Long> count();

    @Delete
    void delete(News news);

    @Query("SELECT * FROM News ORDER BY updatedAt DESC")
    Maybe<List<News>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<News> news);
}
