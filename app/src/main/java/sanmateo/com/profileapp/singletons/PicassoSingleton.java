package sanmateo.com.profileapp.singletons;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class PicassoSingleton {

    private Picasso picasso;
    private static PicassoSingleton PICASSO_SINGLETON = new PicassoSingleton();

    private PicassoSingleton() {

    }

    public static PicassoSingleton getInstance() {
        return PICASSO_SINGLETON;
    }

    public void initPicasso(final Context context, final OkHttpClient okHttpClient) {
        if (picasso == null) {
            picasso = new Picasso.Builder(context)
                    .executor(Executors.newSingleThreadExecutor())
                    .downloader(new OkHttp3Downloader(okHttpClient)).build();
        }
    }

    public Picasso getPicasso() {
        return picasso;
    }
}


