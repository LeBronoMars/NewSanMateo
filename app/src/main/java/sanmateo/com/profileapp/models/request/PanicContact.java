package sanmateo.com.profileapp.models.request;

import io.realm.RealmObject;

/**
 * Created by rsbulanon on 10/2/16.
 */

public class PanicContact extends RealmObject {
    private String contactName;
    private String contactNo;

    public PanicContact(String contactName, String contactNo) {
        this.contactName = contactName;
        this.contactNo = contactNo;
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
