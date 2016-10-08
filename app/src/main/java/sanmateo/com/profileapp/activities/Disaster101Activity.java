package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.TabPagerAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.DisasterFragment;

/**
 * Created by ctmanalo on 10/8/16.
 */

public class Disaster101Activity extends BaseActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_101);
        ButterKnife.bind(this);
        setToolbarTitle("Disaster 101");
        initTabs();
    }

    private void initTabs() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[] {"Earthquake", "Flood", "Tips"};

        /** earthquake */
        fragments.add(DisasterFragment.newInstance(this, getEarthquakeTitle(), getEarthquake()));

        /** flood */
        fragments.add(DisasterFragment.newInstance(this, getFloodTitle(), getFlood()));

        /** tips */
        fragments.add(DisasterFragment.newInstance(this, getTipsTitle(), getTips()));

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    public HashMap<String, List<String>> getEarthquake() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> graphic = new ArrayList<>();
        graphic.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/Graphic_AID.png");

        List<String> hazard = new ArrayList<>();
        hazard.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/HAZARDS_EQ.png");

        List<String> before = new ArrayList<>();
        before.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/BEFORE_EQ.png");

        List<String> during = new ArrayList<>();
        during.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/DURING_EQ.png");

        List<String> after = new ArrayList<>();
        after.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/AFTER_EQ.png");

        expandableListDetail.put(getEarthquakeTitle().get(0), graphic);
        expandableListDetail.put(getEarthquakeTitle().get(1), hazard);
        expandableListDetail.put(getEarthquakeTitle().get(2), before);
        expandableListDetail.put(getEarthquakeTitle().get(3), during);
        expandableListDetail.put(getEarthquakeTitle().get(4), after);

        return expandableListDetail;
    }

    private List<String> getEarthquakeTitle() {
        List<String> titles = new ArrayList<>();
        titles.add("Graphic Aid");
        titles.add("Hazards");
        titles.add("Things to do: Before");
        titles.add("Things to do: During");
        titles.add("Things to do: After");
        return titles;
    }

    public HashMap<String, List<String>> getFlood() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> cause = new ArrayList<>();
        cause.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/causes_flood.png");

        List<String> before = new ArrayList<>();
        before.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/before_flood.png");

        List<String> during = new ArrayList<>();
        during.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/during_flood.png");

        List<String> after = new ArrayList<>();
        after.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/after_flood.png");

        expandableListDetail.put(getFloodTitle().get(0), cause);
        expandableListDetail.put(getFloodTitle().get(1), before);
        expandableListDetail.put(getFloodTitle().get(2), during);
        expandableListDetail.put(getFloodTitle().get(3), after);

        return expandableListDetail;
    }

    private List<String> getFloodTitle() {
        List<String> titles = new ArrayList<>();
        titles.add("Flood Cause");
        titles.add("Things to do: Before");
        titles.add("Things to do: During");
        titles.add("Things to do: After");
        return titles;
    }

    public HashMap<String, List<String>> getTips() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> reminder = new ArrayList<>();
        reminder.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/Reminder.png");

        List<String> emergency = new ArrayList<>();
        emergency.add("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/emergencykit.png");

        expandableListDetail.put(getTipsTitle().get(0), reminder);
        expandableListDetail.put(getTipsTitle().get(1), emergency);

        return expandableListDetail;
    }

    private List<String> getTipsTitle() {
        List<String> titles = new ArrayList<>();
        titles.add("Reminders");
        titles.add("Emergency Kit");
        return titles;
    }
}
