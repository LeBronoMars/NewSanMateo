package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.models.response.News;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class NewsFullPreviewActivity extends BaseActivity {

    @BindView(R.id.iv_image_banner) ImageView iv_image_banner;
    @BindView(R.id.tv_news_title) TextView tv_news_title;
    @BindView(R.id.tv_reported_by) TextView tv_reported_by;
    @BindView(R.id.tv_date_reported) TextView tv_date_reported;
    @BindView(R.id.tv_body) TextView tv_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full_preview);
        ButterKnife.bind(this);
        setToolbarTitle("Back");
        final News n = getIntent().getParcelableExtra("news");

        tv_news_title.setText(n.getTitle());
        tv_reported_by.setText("Reported By : " + n.getReportedBy());
        tv_body.setText(n.getBody());

        try {
            final Date dateReported = getDateFormatter().parse(n.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            tv_date_reported.setText(getSDF().format(calendar.getTime()));
            tv_date_reported.setText(getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
        PicassoSingleton.getInstance().getPicasso().load(n.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .noFade()
                .into(iv_image_banner);
    }
}
