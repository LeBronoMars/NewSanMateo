package sanmateo.com.profileapp.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.singletons.PicassoSingleton;


/**
 * Created by rsbulanon on 7/3/16.
 */
public class PicassoHelper {

    public static void loadImageFromURL(final String url,final int size,final int color,
                                        final ImageView imageView, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        PicassoSingleton.getInstance().getPicasso().load(url)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .noFade()
                .resize(size,size)
                .transform(new CircleTransform(color, 1)).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public static void loadImageFromDrawable(int drawable, ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(drawable).into(imageView);
    }

    public static void loadImageFromDrawable(int drawable, ImageView imageView, int transformColor,
                                             int size) {
        PicassoSingleton.getInstance().getPicasso().load(drawable)
                .resize(size,size)
                .transform(new CircleTransform(transformColor, 1))
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadBlurImageFromURL(final Context context, String url,
                                            final int placeholder,
                                            final int blurStrength,
                                            final ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(url)
                .transform(new StackBlurTransformation(context, blurStrength, 1))
                .fit()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        LogHelper.log("blur", "blurred background successfully loaded");
                    }

                    @Override
                    public void onError() {
                        LogHelper.log("blur", "blurred background not loaded");
                    }
                });
    }

    public static void loadBlurImageFromDrawable(final Context context, final int drawable,
                                                 final int blurStrength, final ImageView imageView) {

        PicassoSingleton.getInstance().getPicasso().load(drawable)
                .transform(new StackBlurTransformation(context, blurStrength, 1))
                .centerCrop()
                .resize(getDisplayWidth(), getDisplayWidth())
                .into(imageView);
    }

    private static int getDisplayWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static void loadImageFromURL(final String url, final ImageView imageView, final ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        PicassoSingleton.getInstance().getPicasso().load(url)
                .fit()
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

    public static void loadImageFromURL(final String url, final ImageView imageView) {
        PicassoSingleton.getInstance().getPicasso().load(url)
                        .fit()
                        .into(imageView);
    }

    public static void loadImageFromURL(final String url,
                                        final int placeHolder,
                                        final ProgressBar progressBar,
                                        final ImageView imageView) {
        Log.d("app", "url to load --> " + url);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        PicassoSingleton.getInstance().getPicasso()
                .load(url)
                .placeholder(placeHolder)
                .error(R.drawable.placeholder_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("app", "on success");
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        Log.d("app", "on error");
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public static void loadImageFromUri(final Uri uri, final ImageView imageView,
                                        final ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        PicassoSingleton.getInstance().getPicasso()
                .load(uri)
                .fit()
                .error(R.drawable.placeholder_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public static void loadImageFromUriGrayscale(final Uri uri, final ImageView imageView,
                                        final ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        PicassoSingleton.getInstance().getPicasso()
                .load(uri)
                .fit()
                .transform(new GrayscaleTransformation())
                .error(R.drawable.placeholder_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
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
