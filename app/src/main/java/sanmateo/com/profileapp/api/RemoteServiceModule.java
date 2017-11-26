package sanmateo.com.profileapp.api;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sanmateo.com.profileapp.BuildConfig;
import sanmateo.com.profileapp.api.user.UserRemoteService;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelRemoteService;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 31/10/2017.
 */
@Module
public class RemoteServiceModule {

    //allowable totalDuration for the app to establish connection with the server.
    private static final int CONNECTION_TIME_OUT = 60;

    //allowable totalDuration for the app to receive response from from the server.
    private static final int READ_TIME_OUT = 60;

    private static final String HEADER = "HEADER";
    private static final String NO_HEADER = "NO_HEADER";
    private static final String LOGGING = "LOGGING";

    @Provides
    @Singleton
    static UserRemoteService provideUserRemoteService(@Named(NO_HEADER) Retrofit retrofit) {
        return retrofit.create(UserRemoteService.class);
    }

    @Provides
    @Singleton
    static WaterLevelRemoteService provideWaterLevelRemoteService(Retrofit retrofit) {
        return retrofit.create(WaterLevelRemoteService.class);
    }

    @Provides
    static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BuildConfig.HOST_NAME)
                                     .client(client)
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .build();
    }

    @Provides
    @Named(NO_HEADER)
    static Retrofit provideRetrofitWithoutHeaders(@Named(NO_HEADER) OkHttpClient
                                                          clientWithoutHeaders) {
        return new Retrofit.Builder().baseUrl(BuildConfig.HOST_NAME)
                                     .client(clientWithoutHeaders)
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .build();
    }

    @SuppressLint("TrulyRandom")
    @Provides
    @Named(NO_HEADER)
    static OkHttpClient provideClientWithoutHeaders(@Named(LOGGING) Interceptor
                                                            loggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
            builder.hostnameVerifier((hostname, session) -> true);
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, getAllTrustingCertificates(), new SecureRandom());
                builder.sslSocketFactory(sslContext.getSocketFactory());
            } catch (NoSuchAlgorithmException | NullPointerException | KeyManagementException e) {
                return builder.build();
            }
            return builder.build();
        }
        return builder.build();
    }

    @Provides
    static OkHttpClient provideClient(@Named(HEADER) Interceptor headerInterceptor,
                                      @Named(LOGGING) Interceptor loggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
            builder.hostnameVerifier((hostname, session) -> true);
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, getAllTrustingCertificates(), new SecureRandom());
                builder.sslSocketFactory(sslContext.getSocketFactory());
            } catch (NoSuchAlgorithmException | NullPointerException | KeyManagementException e) {
                builder.addInterceptor(headerInterceptor);
                return builder.build();
            }
            return builder.build();
        }
        builder.addInterceptor(headerInterceptor);
        return builder.build();
    }

    @Named(HEADER)
    @Provides
    static Interceptor provideHeaderInterceptor(User user) {
        return chain -> {
            Log.d("app", "user token --> " + user.token);
            Request original = chain.request();
            Request request = original.newBuilder()
                                      .header("Authorization", user.token)
                                      .build();
            return chain.proceed(request);
        };
    }

    @Named(LOGGING)
    @Provides
    static Interceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @SuppressWarnings("TrustAllX509TrustManager")
    private static TrustManager[] getAllTrustingCertificates() {
        return new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };
    }
}
