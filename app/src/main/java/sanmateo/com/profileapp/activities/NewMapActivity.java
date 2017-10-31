package sanmateo.com.profileapp.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.MapPagerAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.fragments.NewMapFragment;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.models.others.Location;
import sanmateo.com.profileapp.singletons.BusSingleton;


import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_AUTHENTIC_RESTAURANT;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_BARANGAY;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_HEALTH;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_HERITAGE_SITE;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_RESORT;
import static sanmateo.com.profileapp.helpers.AppConstants.CATEGORY_SHOPPING_MALL;

/**
 * Created by rsbulanon on 20/10/2017.
 */

public class NewMapActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindDrawable(R.drawable.bg_appbar)
    Drawable appBarBg;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);
        unbinder = ButterKnife.bind(this);
        initTabs();
        setUpToolbar();
    }

    private void setUpToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(appBarBg);
        getSupportActionBar().setTitle("Map");
    }

    private void initTabs() {
        ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add("LOCAL GOVT");
        tabTitles.add("NATIONAL GOVT");
        tabTitles.add("TOURIST");

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NewMapFragment.newInstance(getLocalGovernmentOffice()));
        fragments.add(NewMapFragment.newInstance(getNationalGovernmentOffice()));
        fragments.add(NewMapFragment.newInstance(getTouristAndLeisurePlaces()));

        viewPager.setAdapter(new MapPagerAdapter(getSupportFragmentManager(),
                                                 fragments,
                                                 tabTitles));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private ArrayList<Location> getLocalGovernmentOffice() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("San Mateo Municipal Hall",
                                   "General Luna St., Guitnangbayan" +
                                       " I San Mateo, Rizal Philippines",
                                   "+6327067920",
                                   "http://2.bp.blogspot.com/-FB1" +
                                       "vuzJ97l8/UQU-uUzSk3I/AAAAAAAABFs/vxK_9Y3fftA/s1600/rr+%252844%2529.JPG",
                                   new LatLng(14.695081, 121.117839),
                                   AppConstants.CATEGORY_MUNICIPAL));

        locations.add(new Location("Barangay Ampid I",
                                   "E Delos Santos Rd, Ampid-1, San" +
                                       " Mateo, 1830 Rizal",
                                   "+6327067920",
                                   "https://lh6.googleusercontent.com/proxy/li" +
                                       "JgfvGIXWj49cNThzgbJiklXqNuywCCt028xoHIpFUIIa3RKaQPVJl7UUKgl5NxkpiHa3gf7cALoUWD7" +
                                       "nLGxI9YBO33K1sr1FUC6SbMPwMmoV2ZOImAU6GEJj12alvD6Try9vioaFyDMlp1CzDF37SI3w=w455-" +
                                       "h256",
                                   new LatLng(14.681163, 121.119176),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Ampid II",
                                   "San Mateo, Rizal",
                                   "n/a",
                                   "",
                                   new
                                       LatLng(14.686072,
                                              121.119706),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Banaba",
                                   "San Mateo, Rizal",
                                   "n/a",
                                   "",
                                   new
                                       LatLng(14.676173,
                                              121.109715),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Dulong I", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.700409, 121.122562), CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Dulong II", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.700552, 121.126224), CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Guitnang Bayan I",
                                   "San Mateo, Rizal",
                                   "n/a",
                                   "https://geo2.ggpht" +
                                       ".com/cbk?panoid=XNQVyRqna3i94wRCM3kUhw&output=thumbnail&cb_cli" +
                                       "ent=search.TACTILE" +
                                       ".gps&thumb=2&w=408&h=256&yaw=110.2782&pitch=0",
                                   new LatLng(14.695488, 121.121603),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Guitnang Bayan II",
                                   "E Florencio St, Gutinang Bayan" +
                                       " 2, San Mateo, 1850 Rizal",
                                   "+6329974910",
                                   "",
                                   new LatLng(14.697433, 121.123536),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Guinayang", "Patiis Rd, San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.707090, 121.131560), CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Gulod Malaya", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.673623, 121.133290), CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Malanday",
                                   "Tubo Kanan Cor Patiis Road, San Mateo, 1" +
                                       "850 Rizal",
                                   "+6326317662",
                                   "https://lh6.googleusercontent.com/proxy/InPFwYg2Nc35" +
                                       "lcbwl8YQSpxaOwXs3IMqS7XiQbg6TVBsXjTumhZ1BE9lpVAYzY8Uc8wuu2KdZVNma_SjGxy0lt2jDFI2" +
                                       "-qQom4sKpInlhzov_nUwyq97OfOCFP7XvHOwDCrj59eNBUCV0yW9Ivy9OQpM0w=w408-h725",
                                   new LatLng(14.701601, 121.131403),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Maly",
                                   "GSIS Street, Maly, San Mateo, 1850 Rizal",
                                   "n/a",
                                   "https://lh3.googleusercontent" +
                                       ".com/proxy/bfMNreae3Q0hv6AqE5aoWmyWdB4-1ELcs" +
                                       "1vq5iX50Bs99sSwyjIHCgzytTY8lJDdr8YuYLB2iZhVd4-KE9NsSbUhmI_uQuCPy3e75SVz4iMCIFlPH" +
                                       "WaQE4e4zXKWQ70KMDN1mew34qsHcR50_6l40DM_ZA=w455-h256",
                                   new LatLng(14.709491, 121.134087),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Pintong Bukawe", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.675370, 121.208110), CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Silangan",
                                   "Old Army Rd., Silangan, San Mateo, 1850 " +
                                       "Rizal",
                                   "+6329974704",
                                   "https://lh6.googleusercontent.com/proxy/toAMieO2pLtfWV_e" +
                                       "isR4JK9EjFXAOb-kvnTkD0gEnwn2tkK1VyDGXvYJJeTQIEFd0gOPZ0XfpK76orX1RrOuxnUw8Bm6UGUc" +
                                       "tU6lMfFWt8dEtHcwfXzPpmNeEEDz4F8sP-_sLfU78Fy7F4EpI844zldbiw=w408-h725",
                                   new LatLng(14.656925, 121.152168),
                                   CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Sta. Ana", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.692236, 121.115358), CATEGORY_BARANGAY));

        locations.add(new Location("Barangay Sto. Niño",
                                   "Sampaguita Street, San Mateo, Rizal",
                                   "n/a",
                                   "https://geo3.ggpht" +
                                       ".com/cbk?panoid=FKfqmSQIB8tONj6x30YuLg&output=thumbnail" +
                                       "&cb_client=search.TACTILE" +
                                       ".gps&thumb=2&w=408&h=256&yaw=70.554657&pitch=0",
                                   new LatLng(14.669146, 121.134612),
                                   CATEGORY_BARANGAY));

        return locations;
    }

    private ArrayList<Location> getNationalGovernmentOffice() {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(new Location("Police Station", "Gen. Luna Ave, San Mateo, Rizal",
                                   "+6322126558", "", new LatLng(14.695282, 121.117998),
                                   AppConstants.CATEGORY_PEACE_AND_SECURITY));

        locations.add(new Location("Fire Station", "San Mateo, Rizal",
                                   "n/a", "", new LatLng(14.697032, 121.118575),
                                   AppConstants.CATEGORY_PEACE_AND_SECURITY));

        locations.add(new Location("SSS",
                                   "Max's Building, 15 P. Burgos St., Sta. Ana, San Mateo," +
                                       " 1850 Rizal",
                                   "+6329976237",
                                   "https://lh5.googleusercontent.com/proxy/YM_kCxOb14" +
                                       "9D905ivThSLyf_46C8lw90EMGRzcMq5frycB3f6--8N0ZHzLmpFYMvTs3lPpeKwVHleOf8x5Dc7YJQzs" +
                                       "qfmpgfODL8VnKx-cXlhONkMc_v0l9DdKuzOVdQFwkL3rRY3rFu7S-URz5r8tI4JQ=w455-h256",
                                   new LatLng(14.694800, 121.117150),
                                   AppConstants.CATEGORY_SOCIAL_AND_DEVELOPMENT));

        locations.add(new Location("PhilHealth", "Daang Tubo, San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.6693369, 121.0939795), CATEGORY_HEALTH));

        locations.add(new Location("Disaster Management Center", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.695158, 121.117437), CATEGORY_HEALTH));

        locations.add(new Location("Evacuation Center", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.695158, 121.117437), CATEGORY_HEALTH));

        locations.add(new Location("Padre Pio Hospital", "San Mateo, Rizal", "n/a", "",
                                   new LatLng(14.681072, 121.113993), CATEGORY_HEALTH));

        return locations;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_map, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                BusSingleton.getInstance().post(s);
                return false;
            }
        });
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        BusSingleton.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusSingleton.getInstance().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            animateToRight(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Location> getTouristAndLeisurePlaces() {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(new Location("Ciudad Christhia Resort 9 Waves",
                                   "SM San Mateo, Gen. Luna Av" +
                                       "e, Ampid 1, San Mateo, 1850 Rizal",
                                   "+6329976009",
                                   "http://www.9wavesphilippines" +
                                       ".com/images/ciudad-christhia-9-waves.jpg",
                                   new LatLng(14.683196, 121.113316),
                                   CATEGORY_RESORT));

        locations.add(new Location("SM San Mateo",
                                   "Carrieland Country Homes II Ampid San Mateo, " +
                                       "Rizal, San Mateo, 1850 Rizal",
                                   "+639204414806",
                                   "",
                                   new LatLng(14.680076, 121.113787),
                                   CATEGORY_SHOPPING_MALL));

        locations.add(new Location("Puregold San Mateo",
                                   "L4587 General Luna, Banaba, San Mateo, " +
                                       "1850 Rizal",
                                   "+6325701972",
                                   "http://2.bp.blogspot.com/_rmzf_x0bkFs/TCODTadYdEI/A" +
                                       "AAAAAAAKSc/Lqjs5PwDP8A/s1600/Picture+022.jpg",
                                   new LatLng(14.677071, 121.111565),
                                   CATEGORY_SHOPPING_MALL));

        locations.add(new Location("Dap-ayan Azul Resto",
                                   "Divine Mercy village, Guitnang Bayan U" +
                                       "no, San Mateo Rizal, C6 Rd, San Mateo, Rizal",
                                   "+6328611743",
                                   "https://lh3.googl" +
                                       "eusercontent" +
                                       ".com/-X60fdUAxh2k/UewWOiTg8nI/AAAAAAAAAQQ/mx99b36fcjAU_pLJcLPM-uvR0A" +
                                       "4GcrrdQ/s408-k-no/",
                                   new LatLng(14.6851939, 121.1353706),
                                   CATEGORY_AUTHENTIC_RESTAURANT));

        locations.add(new Location("Diocesan Shrine and Parish of Nuestra Señora de Aranzazu",
                                   "Gen. Luna Ave, San Mateo, 1850 Rizal",
                                   "+6325709220",
                                   "https://lh6.googleusercontent" +
                                       ".com/-sJb31HW9g2A/VDnQfIok2FI/AAAAAAAAABs/QtXgtDu_8bw1eNC22OzmE3MjgjV0gBBdg/s408-k-no/",
                                   new LatLng(14.695646, 121.117532),
                                   CATEGORY_HERITAGE_SITE));

        return locations;
    }
}
