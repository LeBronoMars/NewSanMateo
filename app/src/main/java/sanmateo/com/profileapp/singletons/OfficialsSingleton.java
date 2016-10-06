package sanmateo.com.profileapp.singletons;

import java.util.List;

import sanmateo.com.profileapp.models.response.Official;

/**
 * Created by ctmanalo on 10/6/16.
 */

public class OfficialsSingleton {

    private static OfficialsSingleton OFFICIALS = new OfficialsSingleton();
    private List<Official> listOfficials;

    private OfficialsSingleton() {}

    public static OfficialsSingleton getInstance() {
        return OFFICIALS;
    }

    public List<Official> getListOfficials() {
        return listOfficials;
    }
}
