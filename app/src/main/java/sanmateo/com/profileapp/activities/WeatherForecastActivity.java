package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.TabPagerAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.StormWatchFragment;
import sanmateo.com.profileapp.fragments.TodayWeatherFragment;

/**
 * Created by USER on 10/10/2017.
 */

public class WeatherForecastActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        unbinder = ButterKnife.bind(this);

        initTabs();
    }

    private void initTabs() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TodayWeatherFragment.newInstance());
        fragments.add(StormWatchFragment.newInstance());
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"TODAY'S WEATHER", "STORM WATCH"} ));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    @OnClick(R.id.iv_back)
    public void goHome(){
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
