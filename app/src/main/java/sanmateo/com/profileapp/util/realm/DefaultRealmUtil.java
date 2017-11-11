package sanmateo.com.profileapp.util.realm;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by rsbulanon on 07/11/2017.
 */

public class DefaultRealmUtil<T extends RealmModel> implements RealmUtil<T> {

    private Realm realm;

    private Class<T> typeParameterClass;

    @Inject
    public DefaultRealmUtil(Realm realm, Class<T> tClass) {
        this.realm = realm;
        this.typeParameterClass = tClass;
    }

    @Override
    public Single<Long> count() {
        return Single.just(realm.where(typeParameterClass).count());
    }

    @Override
    public Maybe<T> first() {
        T t = realm.where(typeParameterClass).findFirst();

        if (t == null) {
            return Maybe.empty();
        } else {
            return Maybe.just(t);
        }
    }

    @Override
    public Maybe<List<T>> loadAll() {
        return Maybe.just(realm.where(typeParameterClass).findAll());
    }

    @Override
    public Maybe<T> loadById(String id) {
        return Maybe.just(realm.where(typeParameterClass)
                               .equalTo("id", id)
                               .findFirst());
    }

    @Override
    public Completable replaceInto(T t) {
        return openConnection()
                   .andThen(persist(t))
                   .toCompletable()
                   .andThen(commitTransaction());
    }

    private Completable commitTransaction() {
        return Completable.fromAction(() -> realm.commitTransaction());
    }

    private Completable openConnection() {
        return Completable.fromAction(() -> realm.beginTransaction());
    }

    private Single<T> persist(T t) {
        return Single.fromCallable(() -> realm.copyToRealmOrUpdate(t));
    }
}
