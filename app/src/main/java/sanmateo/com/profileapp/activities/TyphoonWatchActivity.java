package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by rsbulanon on 7/14/16.
 */
public class TyphoonWatchActivity extends BaseActivity {

    @BindView(R.id.wv_typhoon_watch) WebView wv_typhoon_watch;
    @BindView(R.id.tv_source) TextView tv_source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typhoon_watch);
        ButterKnife.bind(this);
        wv_typhoon_watch.getSettings().setJavaScriptEnabled(true);
        wv_typhoon_watch.loadUrl("http://kidlat.pagasa.dost.gov.ph/");
        tv_source.setText("Source : www.pagasa.dost.gov.ph");
        setToolbarTitle("Typhoon Watch");
    }
}
