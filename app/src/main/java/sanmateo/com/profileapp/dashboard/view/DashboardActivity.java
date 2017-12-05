package sanmateo.com.profileapp.dashboard.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.incident.list.view.IncidentListFragment;
import sanmateo.com.profileapp.waterlevel.view.WaterLevelFragment;

/**
 * Created by rsbulanon on 19/11/2017.
 */

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        inflateFragments();
    }

    private void inflateFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.water_level_container, WaterLevelFragment.newInstance());
        fragmentTransaction.add(R.id.incidents_container, IncidentListFragment.newInstance());
        fragmentTransaction.commit();
    }
}
