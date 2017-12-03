package sanmateo.com.profileapp.util.glide;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rsbulanon on 03/12/2017.
 */
@Module
public interface ImageLoaderModule {

    @Binds
    GlideImageLoader bindGlideImageLoader(DefaultGlideImageLoader defaultGlideImageLoader);
}
