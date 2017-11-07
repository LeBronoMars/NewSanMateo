package sanmateo.com.profileapp.api;

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
import okhttp3.logging.HttpLoggingInterceptor;
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

    @Provides
    static OkHttpClient provideClient(Interceptor loggingInterceptor) {
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
