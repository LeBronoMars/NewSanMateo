package sanmateo.com.profileapp.helpers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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

    /** get current user */
    public T findOne(final Class<T> tClass) {
        final RealmQuery<T> query = realm.where(tClass);
        return query.findFirst();
    }

    /** add record */
    public void createRecord(final T tClass) {
        realm.beginTransaction();
        realm.copyToRealm(tClass);
        realm.commitTransaction();
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

}
