package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.CprAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.CprFragment;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class CprActivity extends BaseActivity {
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.iv_footer) ImageView iv_footer;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpr);
        ButterKnife.bind(this);
        setToolbarTitle("How to CPR");
        initViewPager();
        PicassoSingleton.getInstance().getPicasso().load(AppConstants.IMAGE_URL_CPR_AMERICAN_HEART)
                .noFade()
                .into(iv_footer);
    }

    private void initViewPager() {
        fragments.add(CprFragment.newInstance(AppConstants.IMAGE_URL_CPR_INFANT));
        fragments.add(CprFragment.newInstance(AppConstants.IMAGE_URL_CPR_ADULT));
        viewPager.setAdapter(new CprAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

    @OnClick(R.id.tvLink)
    public void goToLink() {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.heart.org")));
    }
}
