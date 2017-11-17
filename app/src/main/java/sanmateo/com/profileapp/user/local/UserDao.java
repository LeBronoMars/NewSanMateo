package sanmateo.com.profileapp.user.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Maybe;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 17/11/2017.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM User LIMIT 1")
    Maybe<User> findOne();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);
}
