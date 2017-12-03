package sanmateo.com.profileapp.util.glide;

import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;

import javax.inject.Inject;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.framework.GlideApp;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DefaultGlideImageLoader implements GlideImageLoader {

    @Inject
    public DefaultGlideImageLoader() {
    }

    @Override
    public void loadImage(String imageUrl, ImageView imageView) {
        GlideApp.with(imageView.getContext())
                .load(new GlideUrl(imageUrl))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
