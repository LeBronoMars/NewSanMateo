package sanmateo.com.profileapp.models.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rsbulanon on 10/2/16.
 */

public class PanicContact extends RealmObject {
    @PrimaryKey private String id;
    private String contactName;
    private String contactNo;

    public PanicContact() {
    }

    public PanicContact(String id, String contactName, String contactNo) {
        this.id = id;
        this.contactName = contactName;
        this.contactNo = contactNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
