package sanmateo.com.profileapp.incident.list;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import sanmateo.com.profileapp.incident.list.presenter.IncidentListPresenterModule;
import sanmateo.com.profileapp.incident.list.view.IncidentListFragment;

@Subcomponent(modules = IncidentListPresenterModule.class)
@IncidentListScope
public interface IncidentListSubcomponent extends AndroidInjector<IncidentListFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<IncidentListFragment> {
    }
}
