package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
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
 * Created by rsbulanon on 6/28/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> news;
    private Context context;
    private BaseActivity activity;
    private OnSelectNewsListener onSelectNewsListener;

    public NewsAdapter(final Context context, final ArrayList<News> news) {
        this.news = news;
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_news) LinearLayout ll_news;
        @BindView(R.id.iv_image_url) ImageView iv_image_url;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_reported_by) TextView tv_reported_by;
        @BindView(R.id.tv_date_reported) TextView tv_date_reported;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final News n = news.get(i);
        holder.tv_title.setText(n.getTitle());
        holder.tv_reported_by.setText(n.getReportedBy());
        try {
            final Date dateReported = activity.getDateFormatter().parse(n.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tv_date_reported.setText(activity.getSDF().format(calendar.getTime()));
            holder.tv_date_reported.setText(activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
        PicassoSingleton.getInstance().getPicasso().load(n.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.iv_image_url);

        holder.ll_news.setOnClickListener(view -> {
            if (onSelectNewsListener != null) {
                onSelectNewsListener.onSelectedNews(n);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnSelectNewsListener {
        void onSelectedNews(final News n);
    }

    public void setOnSelectNewsListener(OnSelectNewsListener onSelectNewsListener) {
        this.onSelectNewsListener = onSelectNewsListener;
    }
}
