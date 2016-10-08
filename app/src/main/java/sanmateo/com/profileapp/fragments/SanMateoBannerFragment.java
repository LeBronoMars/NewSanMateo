package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.singletons.PicassoSingleton;


/**
 * Created by rsbulanon on 6/26/16.
 */
public class SanMateoBannerFragment extends Fragment {
    @BindView(R.id.civ_san_mateo_logo) CircleImageView civ_san_mateo_logo;
    @BindView(R.id.pb_load_image) ProgressBar pb_load_image;

    public static SanMateoBannerFragment newInstance() {
        return new SanMateoBannerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_san_mateo, null, false);
        ButterKnife.bind(this, view);
        PicassoSingleton.getInstance().getPicasso().load(AppConstants.SAN_MATEO_LOGO)
                .fit().centerCrop()
                .into(civ_san_mateo_logo, new Callback() {
                    @Override
                    public void onSuccess() {
                        pb_load_image.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pb_load_image.setVisibility(View.GONE);
                    }
                });
        return view;
    }
}
