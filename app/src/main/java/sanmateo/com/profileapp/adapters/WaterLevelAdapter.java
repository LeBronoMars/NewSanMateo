package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import sanmateo.com.profileapp.models.response.WaterLevel;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class WaterLevelAdapter extends RecyclerView.Adapter<WaterLevelAdapter.ViewHolder> {

    private ArrayList<WaterLevel> waterLevels;
    private Context context;
    private BaseActivity activity;

    public WaterLevelAdapter(final Context context, final ArrayList<WaterLevel> waterLevels) {
        this.waterLevels = waterLevels;
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return waterLevels.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_water_level, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_water_level) TextView tv_water_level;
        @BindView(R.id.tv_alarm_level) TextView tv_alarm_level;
        @BindView(R.id.tv_date_posted) TextView tv_date_posted;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final WaterLevel w = waterLevels.get(i);

        holder.tv_water_level.setText(w.getWaterLevel()+"");
        holder.tv_alarm_level.setText(w.getAlert());

        if (w.getWaterLevel() >= 18.01 && w.getWaterLevel() <= 19.00) {
            holder.tv_alarm_level.setTextColor(ContextCompat.getColor(context,R.color.water_level_alarm));
        } else if (w.getWaterLevel() >= 19.01) {
            holder.tv_alarm_level.setTextColor(ContextCompat.getColor(context,R.color.water_level_critical));
        }
        try {
            final Date dateReported = activity.getDateFormatter().parse(w.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tv_date_posted.setText(activity.getSDF().format(calendar.getTime()));
            holder.tv_date_posted.setText(activity.getPrettyTime().format(calendar.getTime()));
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
