package sanmateo.com.profileapp;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import sanmateo.com.profileapp.framework.MockProfileApplication;

/**
 * Created by rsbulanon on 11/11/2017.
 */

public class MockTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockProfileApplication.class.getName(), context);
    }
}
