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
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class CprFragment extends Fragment {
    @BindView(R.id.iv_cpr) ImageView iv_cpr;
    @BindView(R.id.pb_load_image) ProgressBar pb_load_image;
    private String url;

    public static CprFragment newInstance(String url) {
        final CprFragment cprFragment = new CprFragment();
        cprFragment.url = url;
        return cprFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpr, container, false);
        ButterKnife.bind(this, view);
        PicassoSingleton.getInstance().getPicasso().load(url)
                .noFade()
                .placeholder(R.drawable.placeholder_image)
                .into(iv_cpr, new Callback() {
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
