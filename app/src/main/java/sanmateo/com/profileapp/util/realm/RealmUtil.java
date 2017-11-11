package sanmateo.com.profileapp.util.realm;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.realm.RealmObject;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public interface RealmUtil<T extends RealmObject> {

    Single<Long> count();

    Maybe<T> first();

    Maybe<List<T>> loadAll();

    Maybe<T> loadById(String id);

    Completable replaceInto(T t);
}
