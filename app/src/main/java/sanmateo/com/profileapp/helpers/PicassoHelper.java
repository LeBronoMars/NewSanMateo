package sanmateo.com.profileapp.helpers;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.singletons.PicassoSingleton;


/**
 * Created by rsbulanon on 7/3/16.
 */
public class PicassoHelper {

    public static void loadImageFromURL(final String url,final int size,final int color,
                                        final ImageView imageView, final ProgressBar progressBar) {
        LogHelper.log("pic","load pic url ---> " + url);
        progressBar.setVisibility(View.VISIBLE);
        PicassoSingleton.getInstance().getPicasso().load(url)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .resize(size,size)
                .transform(new CircleTransform(color, 1)).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                LogHelper.log("pic","profile pic loaded successfully");
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                LogHelper.log("pic","profile pic loaded unable to load");
            }
        });
    }

    public static void loadImageFromDrawable(int drawable, ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(drawable).into(imageView);
    }

    public static void loadImageFromDrawable(int drawable, ImageView imageView, int transformColor,
                                             int size) {
        PicassoSingleton.getInstance().getPicasso().load(drawable)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .resize(size,size)
                .transform(new CircleTransform(transformColor, 1))
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadBlurImageFromURL(String url, int placeholder, int blurStrength, ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(url)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(placeholder)
                .transform(new StackBlurTransformation(blurStrength))
                .into(imageView);
    }

    public static void loadBlurImageFromURL(String url, int placeholder, int size, int blurStrength, ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(url)
                .resize(size,size)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(placeholder)
                .transform(new StackBlurTransformation(blurStrength))
                .into(imageView);
    }

    public static void loadBlurImageFromDrawable(int drawable, int blurStrength, ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(drawable)
                .fit()
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(drawable)
                .transform(new StackBlurTransformation(blurStrength))
                .into(imageView);
    }

    public static void loadImageFromURL(final String url, final ImageView imageView, final ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        PicassoSingleton.getInstance().getPicasso().load(url)
                .fit()
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("pic", "Image loading done -> " + url);
                    }

                    @Override
                    public void onError() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("pic", "Image loading failed");
                    }
                });
    }

    public static void loadImageFromURL(final String url, final int size, final ImageView imageView,
                                        final ProgressBar progressBar, Context context) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        Display display = ((BaseActivity)context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        PicassoSingleton.getInstance().getPicasso().load(url)
                .resize(width, size)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("pic", "Image loading done -> " + url);
                    }

                    @Override
                    public void onError() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("pic", "Image loading failed");
                    }
                });
    }

}
