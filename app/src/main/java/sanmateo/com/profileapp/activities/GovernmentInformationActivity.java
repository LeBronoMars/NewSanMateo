package sanmateo.com.profileapp.activities;

import android.os.Bundle;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by ctmanalo on 10/7/16.
 */

public class GovernmentInformationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_information);
        setToolbarTitle("Government Information");
    }
}
