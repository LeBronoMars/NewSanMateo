package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.DisasterMenuAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.DisasterMgtMenuDialogFragment;

/**
 * Created by avinnovz on 4/15/17.
 */

public class DisasterManagementActivity extends BaseActivity{

    @BindView(R.id.lv_disaster_menu)
    ListView lv_disaster_menu;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_management);
        unbinder = ButterKnife.bind(this);
        setToolbarTitle("Disaster Management");
        initList();
    }

    private void initList() {
        ArrayList<String> menu = new ArrayList<>();
        menu.add("Emergency Numbers");
        menu.add("Emergency Kit");
        menu.add("How to CPR");
        menu.add("Disaster 101");
        menu.add("Public Announcements");
        menu.add("Typhoon Watch");
        menu.add("Water Level Monitoring");
        menu.add("Alert Level(Water Level)");
        menu.add("Global Disaster Monitoring");

        final DisasterMenuAdapter adapter = new DisasterMenuAdapter(this, menu);
        lv_disaster_menu.setAdapter(adapter);
        lv_disaster_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    moveToOtherActivity(HotlinesActivity.class);
                } else if (position == 1) {
                    moveToOtherActivity(EmergencyKitActivity.class);
                } else if (position == 2) {
                    moveToOtherActivity(CprActivity.class);
                } else if (position == 3) {
                    moveToOtherActivity(Disaster101Activity.class);
                } else if (position == 4) {
                    moveToOtherActivity(PublicAnnouncementsActivity.class);
                } else if (position == 5) {
                    moveToOtherActivity(TyphoonWatchActivity.class);
                } else if (position == 6) {
                    moveToOtherActivity(WaterLevelMonitoringActivity.class);
                } else if (position == 7) {
                    moveToOtherActivity(AlertLevelActivity.class);
                } else if (position == 8) {
                    moveToOtherActivity(GlobalDisasterActivity.class);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
