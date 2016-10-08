package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.HotlinesAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.others.Hotlines;


/**
 * Created by rsbulanon on 7/14/16.
 */
public class HotlinesActivity extends BaseActivity {
    @BindView(R.id.rv_cards) RecyclerView rv_cards;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlines_card);
        ButterKnife.bind(this);
        setToolbarTitle("Emergency Hotlines");
        ArrayList<Hotlines> hotlines = new ArrayList<>();

        ArrayList<String> pnp = new ArrayList<>();
        ArrayList<String> fire = new ArrayList<>();
        ArrayList<String> icco = new ArrayList<>();
        ArrayList<String> rescue = new ArrayList<>();

        pnp.addAll(Arrays.asList(new String[]{"026611480","022126558"}));
        fire.addAll(Arrays.asList(new String[]{"25701156"}));
        icco.addAll(Arrays.asList(new String[]{"027756845","025706846"}));
        rescue.addAll(Arrays.asList(new String[]{"27816820"}));

        hotlines.add(new Hotlines("PNP", "", pnp));
        hotlines.add(new Hotlines("FIRE", "", fire));
        hotlines.add(new Hotlines("ICCO", "", icco));
        hotlines.add(new Hotlines("RESCUE", "", rescue));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_cards.setLayoutManager(llm);
        rv_cards.setAdapter(new HotlinesAdapter(this,hotlines));
    }
}
