package sanmateo.com.profileapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class MapPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Local Government Office", "National Government Office",
            "Tourist & Leisure Place" };
    private ArrayList<Fragment> fragments;

    public MapPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
