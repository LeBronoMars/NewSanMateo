package sanmateo.com.profileapp.helpers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import sanmateo.com.profileapp.models.realm.PanicContact;
import sanmateo.com.profileapp.models.response.AuthResponse;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class RealmHelper<T extends RealmObject> {
    private Realm realm;

    public RealmHelper() {
        realm = Realm.getDefaultInstance();
    }

    /** get all */
    public RealmResults<T> findAll(final Class<T> tClass) {
        final RealmQuery<T> query = realm.where(tClass);
        return query.findAll();
    }

    public RealmResults<T> findAll(final Class<T> tClass, final String field, final Sort sort) {
        final RealmQuery<T> query = realm.where(tClass);
        return query.findAllSorted(field, sort);
    }

    /** get current user */
    public T findOne(final Class<T> tClass) {
        final RealmQuery<T> query = realm.where(tClass);
        return query.findFirst();
    }

    /** add record */
    public void replaceInto(final T tClass) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tClass);
        realm.commitTransaction();
    }

    public void openRealm() {
        realm.beginTransaction();
    }

    public void commitTransaction() {
        realm.commitTransaction();
    }

    public void update(final T tClass) {
        realm.copyToRealmOrUpdate(tClass);
    }

    /** delete contact by contact no */
    public void deleteContact(final String contactNo) {
        realm.beginTransaction();
        final RealmQuery<PanicContact> query = realm.where(PanicContact.class);
        query.equalTo("contactNo", contactNo);
        query.findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    /** delete current user */
    public void deleteCurrentUser() {
        realm.beginTransaction();
        final RealmQuery<AuthResponse> query = realm.where(AuthResponse.class);
        query.findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    /** check if contact is already existing */
    public boolean isExisting(final String contactNo) {
        final RealmQuery<PanicContact> query = realm.where(PanicContact.class);
        query.equalTo("contactNo", contactNo);
        return query.count() > 0;
    }

    /** get count */
    public long count(final Class<T> tClass) {
        final RealmQuery<T> query = realm.where(tClass);
        return query.count();
    }

}
