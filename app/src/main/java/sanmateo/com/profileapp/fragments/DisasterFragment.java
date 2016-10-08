package sanmateo.com.profileapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.DisasterAdapter;

/**
 * Created by ctmanalo on 10/8/16.
 */

public class DisasterFragment extends Fragment {

    @BindView(R.id.expandable_list_view) ExpandableListView expandableListView;
    private Context context;
    private List<String> title;
    private HashMap<String, List<String>> details;

    public static DisasterFragment newInstance(Context context, List<String> title,
                                               HashMap<String, List<String>> details) {
        DisasterFragment fragment = new DisasterFragment();
        fragment.context = context;
        fragment.title = title;
        fragment.details = details;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_disaster_101, container, false);
        ButterKnife.bind(this, view);
        initList();
        return view;
    }

    private void initList() {
        DisasterAdapter adapter = new DisasterAdapter(context, title, details);
        expandableListView.setAdapter(adapter);
    }
}
