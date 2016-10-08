package sanmateo.com.profileapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class CprFragment extends Fragment {
    @BindView(R.id.iv_cpr) ImageView iv_cpr;
    private String type;

    public static CprFragment newInstance(String type) {
        final CprFragment cprFragment = new CprFragment();
        cprFragment.type = type;
        return cprFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpr, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switch (type) {
            case "Infant":
                iv_cpr.setImageResource(R.drawable.cpr_infant);
                break;
            case "Adult":
                iv_cpr.setImageResource(R.drawable.cpr_adult);
                break;
        }
    }
}
