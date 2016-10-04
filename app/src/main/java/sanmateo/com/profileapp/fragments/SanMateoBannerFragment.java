package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sanmateo.com.profileapp.R;


/**
 * Created by rsbulanon on 6/26/16.
 */
public class SanMateoBannerFragment extends Fragment {

    public static SanMateoBannerFragment newInstance() {
        return new SanMateoBannerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_san_mateo, null, false);
        return view;
    }
}
