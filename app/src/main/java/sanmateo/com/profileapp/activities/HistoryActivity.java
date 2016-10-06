package sanmateo.com.profileapp.activities;

import android.os.Bundle;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by ctmanalo on 10/6/16.
 */

public class HistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setToolbarTitle("History");
    }
}
