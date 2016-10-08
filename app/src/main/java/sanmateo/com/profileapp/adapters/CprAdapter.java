package sanmateo.com.profileapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class CprAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Infant CPR", "Adult CPR"};
    private ArrayList<Fragment> fragments;

    public CprAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
