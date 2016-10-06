package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.InformationAdapter;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by ctmanalo on 10/5/16.
 */

public class InformationActivity extends BaseActivity {

    @BindView(R.id.lv_information) ListView lvInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        setToolbarTitle("Information");
        initInformationList();
    }

    private void initInformationList() {
        lvInformation.setAdapter(new InformationAdapter(this));
        lvInformation.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                        moveToOtherActivity(HistoryActivity.class);
                    break;
                case 1:
                        moveToOtherActivity(OfficialsActivity.class);
                    break;
                case 2:
                        moveToOtherActivity(GovernmentInformationActivity.class);
                    break;
            }
        });
    }
}
