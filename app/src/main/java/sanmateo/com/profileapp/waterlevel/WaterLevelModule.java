package sanmateo.com.profileapp.waterlevel;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import sanmateo.com.profileapp.waterlevel.local.RoomWaterLevelModule;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevelUseCaseModule;
import sanmateo.com.profileapp.waterlevel.usecase.remote.WaterLevelRemoteModule;
import sanmateo.com.profileapp.waterlevel.view.WaterLevelFragment;

@Module(subcomponents = WaterLevelSubcomponent.class,
    includes = {
                   WaterLevelUseCaseModule.class,
    }
)
public interface WaterLevelModule {

    @Binds
    @IntoMap
    @FragmentKey(WaterLevelFragment.class)
    AndroidInjector.Factory<? extends Fragment>
        bindWaterLevelFragment(WaterLevelSubcomponent.Builder builder);
}
