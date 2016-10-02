package sanmateo.com.profileapp.helpers;

import android.util.Log;

import sanmateo.com.profileapp.BuildConfig;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class LogHelper {

    public static void log(final String key, final String message) {
        if (BuildConfig.DEBUG) {
            Log.d(key,message);
        }
    }
}
