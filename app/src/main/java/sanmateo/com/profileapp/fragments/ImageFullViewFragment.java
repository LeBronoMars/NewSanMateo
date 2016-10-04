package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ImageFullViewFragment extends Fragment {

    @BindView(R.id.iv_full_image) ImageView iv_full_image;
    private String url;

    public static ImageFullViewFragment newInstance(final String url) {
        final ImageFullViewFragment fragment = new ImageFullViewFragment();
        fragment.url = url;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_image_full_view,container,false);
        ButterKnife.bind(this,view);
        PicassoSingleton.getInstance().getPicasso().load(url).placeholder(R.drawable.placeholder_image)
                .centerCrop().fit().into(iv_full_image);
        return view;
    }
}
