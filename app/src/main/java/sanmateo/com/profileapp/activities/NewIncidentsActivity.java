package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.TabPagerAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.IncidentReportFragment;
import sanmateo.com.profileapp.fragments.TodayWeatherFragment;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;

/**
 * Created by USER on 10/13/2017.
 */

public class NewIncidentsActivity extends BaseActivity implements OnApiRequestListener{

    private Unbinder unbinder;
    private static final String REPORT_PREFIX = "Reports: ";
    private String[] tabLabels;

    private ApiRequestHelper apiRequestHelper;
    private String token;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.tv_tab_name)
    TextView tvTabName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_incidents);
        unbinder = ButterKnife.bind(this);

        initTabs();
        fetchIncidents();
    }

    private void initTabs() {
        tabLabels = new String[] {getString(R.string.traffic_road),
                getString(R.string.solid_waste), getString(R.string.flooding), getString(R.string.fire),
                getString(R.string.miscellaneous)};
        tvTabName.setText(REPORT_PREFIX + tabLabels[0]);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(IncidentReportFragment.newInstance("", new ArrayList<>()));
        fragments.add(IncidentReportFragment.newInstance("", new ArrayList<>()));
        fragments.add(IncidentReportFragment.newInstance("", new ArrayList<>()));
        fragments.add(IncidentReportFragment.newInstance("", new ArrayList<>()));
        fragments.add(IncidentReportFragment.newInstance("", new ArrayList<>()));
        int[] iconList = new int[] {R.drawable.ic_road_24dp, R.drawable.ic_waste_24dp,
                R.drawable.ic_floods_24dp, R.drawable.ic_fire_24dp, R.drawable.ic_misc_24dp};
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"", "", "", "", ""} ));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.getTabAt(i).setIcon(iconList[i]);
        }
        setHighlight(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                updateTabLabel(position);
                setHighlight(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    private void setHighlight(int position) {
        for (int i = 0; i < 5; i++) {
            int alpha = i == position ? 255 : 128;
            tabLayout.getTabAt(i).getIcon().setAlpha(alpha);
        }
    }

    private void updateTabLabel(final int position) {
       tvTabName.setText(REPORT_PREFIX + tabLabels[position]);
    }

    private void fetchIncidents() {
        token = CurrentUserSingleton.getInstance().getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        apiRequestHelper.getAllIncidents(token, 0, null, "active");
    }

    @OnClick(R.id.iv_report)
    public void fileIncident() {
        startActivity(new Intent(this, FileIncidentActivity.class));
        animateToLeft(this);
    }

    @OnClick(R.id.iv_back)
    public void goHome() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        showCustomProgress("Fetching incidents, Please wait...");
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();
        showToast("size: " + ((ArrayList<Incident>) result).size());
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        showToast("failed: " + t.getMessage());
    }
}
