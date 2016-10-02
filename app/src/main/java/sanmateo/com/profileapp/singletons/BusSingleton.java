package sanmateo.com.profileapp.singletons;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class BusSingleton {

    private static Bus BUS = new Bus(ThreadEnforcer.ANY);

    private BusSingleton() {}

    public static Bus getInstance() {
        return BUS;
    }
}
