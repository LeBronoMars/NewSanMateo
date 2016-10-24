package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.response.Official;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by rsbulanon on 9/3/16.
 */
public class OfficialFullInfoActivity extends BaseActivity {

    @BindView(R.id.civ_pic) CircleImageView civ_pic;
    @BindView(R.id.pb_load_image) ProgressBar pb_load_image;
    @BindView(R.id.tv_official_name) TextView tv_official_name;
    @BindView(R.id.tv_position) TextView tv_position;
    @BindView(R.id.tv_background) TextView tv_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_full_info);
        ButterKnife.bind(this);
        setToolbarTitle("San Mateo Officials");

        final Official official = (Official) getIntent().getSerializableExtra("official");
        final String nickName = official.getNickName().isEmpty() ? " " : " '"+official.getNickName()+"' ";
        final String fullName = official.getFirstName() + nickName + official.getLastName();
        final String position = official.getPosition();
        final String background = official.getBackground();
        final String picUrl = official.getPic();

        tv_official_name.setText(fullName);
        tv_position.setText(position);
        pb_load_image.setVisibility(View.VISIBLE);
        tv_background.setText(background);
        if (picUrl != null && !picUrl.isEmpty()) {
            PicassoSingleton.getInstance().getPicasso().load(picUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .fit()
                    .noFade()
                    .into(civ_pic, new Callback() {
                        @Override
                        public void onSuccess() {
                            pb_load_image.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            pb_load_image.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
