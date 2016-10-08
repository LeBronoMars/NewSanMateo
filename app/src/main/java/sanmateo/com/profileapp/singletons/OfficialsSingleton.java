package sanmateo.com.profileapp.singletons;

import java.util.ArrayList;
import java.util.List;

import sanmateo.com.profileapp.models.response.Official;

/**
 * Created by ctmanalo on 10/6/16.
 */

public class OfficialsSingleton {
    private static OfficialsSingleton OFFICIALS = new OfficialsSingleton();
    private ArrayList<Official> listOfficials = new ArrayList<>();

    private OfficialsSingleton() {}

    public static OfficialsSingleton getInstance() {
        return OFFICIALS;
    }

    public List<Official> getListOfficials() {
        return listOfficials;
    }

    public void setListOfficials(ArrayList<Official> listOfficials) {
        this.listOfficials.clear();
        this.listOfficials = listOfficials;
    }
}
