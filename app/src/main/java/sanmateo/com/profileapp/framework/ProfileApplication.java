package sanmateo.com.profileapp.framework;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Service;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.HasServiceInjector;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public class ProfileApplication extends Application implements HasActivityInjector,
                                                               HasServiceInjector,
                                                               HasFragmentInjector {

    // Allows us to inject on Activity classes
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    // Allows us to inject on Service classes
    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    // Allows us to inject on Fragment classes
    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent().inject(this);
    }

    ProfileComponent initializeComponent() {
        return DaggerProfileComponent.builder().application(this).build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return supportFragmentDispatchingAndroidInjector;
    }
}
