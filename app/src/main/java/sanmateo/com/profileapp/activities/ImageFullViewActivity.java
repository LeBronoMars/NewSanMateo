package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.FullImageViewAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.ImageFullViewFragment;
import sanmateo.com.profileapp.models.response.Incident;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ImageFullViewActivity extends BaseActivity {

    @BindView(R.id.vp_images)
    ViewPager vp_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullview);
        ButterKnife.bind(this);
        final Incident incident = getIntent().getParcelableExtra("incident");
        final int selectedImagePosition = getIntent().getIntExtra("selectedImagePosition",1);
        final ArrayList<String> incidentImages = new ArrayList<>();
        /**
         * if incident images contains '||' which acts as the delimiter
         * split incident.getImages() using '||' to get the list of image urls
         * */
        if (incident.getImages().contains("###")) {
            incidentImages.addAll(Arrays.asList(incident.getImages().split("###")));
        } else {
            incidentImages.add(incident.getImages());
        }
        setToolbarTitle("Selected Image " + (selectedImagePosition+1) + "/"+incidentImages.size());

        /** initialize view pager */
        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (String  s : incidentImages) {
            fragments.add(ImageFullViewFragment.newInstance(s));
        }
        final FullImageViewAdapter adapter = new FullImageViewAdapter(getSupportFragmentManager(),fragments);
        vp_images.setAdapter(adapter);
        vp_images.setOffscreenPageLimit(fragments.size());
        vp_images.setCurrentItem(selectedImagePosition);
        vp_images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateToolbarTitle("Selected Image " + (position + 1) + "/" + incidentImages.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
