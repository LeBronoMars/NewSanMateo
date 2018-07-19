package sanmateo.com.profileapp.base;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import java.io.File;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import sanmateo.com.profileapp.BuildConfig;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.singletons.PicassoSingleton;
import sanmateo.com.profileapp.singletons.RetrofitSingleton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /** initialize facebook sdk */
        FacebookSdk.sdkInitialize(this);

        /** initialize fabric */
        Fabric.with(this, new Crashlytics());

        /** initialize realm */
        Realm.init(this);

        /** initialize okhttp client */
        File cacheDir = getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = getCacheDir();
        }
        final Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /** initialize ok http client */
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            final Request request = chain.request();
                            LogHelper.log("api","performing url --> " + request.url());
                            return chain.proceed(request);
                        })
                .addInterceptor(interceptor)
                .cache(cache)
                .build();

        /** initialize picasso */
        final PicassoSingleton picassoSingleton = PicassoSingleton.getInstance();
        picassoSingleton.initPicasso(this, okHttpClient);

        /** initialize retrofit */
        final RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        retrofitSingleton.initRetrofit(BuildConfig.HOST_NAME, okHttpClient);

        /** initialize calligraphy */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Raleway-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
