package sanmateo.com.profileapp.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sanmateo.com.profileapp.BuildConfig;
import sanmateo.com.profileapp.api.user.UserRemoteService;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Module
public class RemoteServiceModule {

    //allowable totalDuration for the app to establish connection with the server.
    private static final int CONNECTION_TIME_OUT = 60;

    //allowable totalDuration for the app to receive response from from the server.
    private static final int READ_TIME_OUT = 60;

    @Provides
    @Singleton
    static UserRemoteService provideUserRemoteService(Retrofit retrofit) {
        return retrofit.create(UserRemoteService.class);
    }

    @Provides
    static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BuildConfig.HOST_NAME)
                                     .client(client)
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .build();
    }
}
