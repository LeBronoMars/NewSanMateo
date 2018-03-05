package sanmateo.com.profileapp.singletons;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sanmateo.com.profileapp.interfaces.ApiInterface;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class RetrofitSingleton {

    private static RetrofitSingleton RETROFIT_SINGLETON = new RetrofitSingleton();
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    private RetrofitSingleton() {

    }

    public static RetrofitSingleton getInstance() {
        return RETROFIT_SINGLETON;
    }

    public void initRetrofit(final String baseUrl, final OkHttpClient okHttpClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}
