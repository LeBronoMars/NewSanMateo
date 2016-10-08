package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sanmateo.com.profileapp.models.response.Announcement;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolder> {

    private ArrayList<Announcement> announcements;
    private Context context;
    private BaseActivity activity;

    public AnnouncementsAdapter(final Context context, final ArrayList<Announcement> announcements) {
        this.announcements = announcements;
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announcements, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_message) TextView tv_message;
        @BindView(R.id.tv_date_announced) TextView tv_date_announced;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Announcement a = announcements.get(i);
        holder.tv_title.setText(a.getTitle());
        holder.tv_message.setText(a.getMessage());
        try {
            final Date dateReported = activity.getDateFormatter().parse(a.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tv_date_announced.setText(activity.getSDF().format(calendar.getTime()));
            holder.tv_date_announced.setText(activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
