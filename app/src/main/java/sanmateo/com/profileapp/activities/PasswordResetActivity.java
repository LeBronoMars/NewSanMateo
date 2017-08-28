package sanmateo.com.profileapp.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by USER on 8/28/2017.
 */

public class PasswordResetActivity extends BaseActivity {
    
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void cancelActivity() {
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
