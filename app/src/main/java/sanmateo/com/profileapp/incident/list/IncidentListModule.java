package sanmateo.com.profileapp.incident.list;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import sanmateo.com.profileapp.incident.list.view.IncidentListFragment;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@Module(subcomponents = IncidentListSubcomponent.class)
public interface IncidentListModule {

    @Binds
    @IntoMap
    @FragmentKey(IncidentListFragment.class)
    AndroidInjector.Factory<? extends Fragment>
        bindFragment(IncidentListSubcomponent.Builder builder);
}
