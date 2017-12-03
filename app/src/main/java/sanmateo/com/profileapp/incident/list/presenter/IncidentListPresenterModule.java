package sanmateo.com.profileapp.incident.list.presenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@Module
public interface IncidentListPresenterModule {

    @Binds
    IncidentListPresenter bindIncidentListPresenter(
        DefaultIncidentListPresenter defaultIncidentListPresenter);
}
