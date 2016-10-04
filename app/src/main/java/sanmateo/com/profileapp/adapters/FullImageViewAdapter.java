package sanmateo.com.profileapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class FullImageViewAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;

    public FullImageViewAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
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
    public int getItemPosition(Object object) {
        if (fragmentManager.getFragments().contains(object)) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }
}
