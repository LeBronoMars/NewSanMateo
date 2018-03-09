package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.models.response.Notification;
import sanmateo.com.profileapp.models.response.NotificationType;


import static android.support.v4.content.ContextCompat.getDrawable;
import static sanmateo.com.profileapp.models.response.Incident.FIRE;
import static sanmateo.com.profileapp.models.response.Incident.FLOODING;
import static sanmateo.com.profileapp.models.response.Incident.MISCELLANEOUS;
import static sanmateo.com.profileapp.models.response.Incident.SOLID_WASTE;
import static sanmateo.com.profileapp.models.response.Incident.TRAFFIC_ROAD;
import static sanmateo.com.profileapp.models.response.NotificationType.INCIDENT;
import static sanmateo.com.profileapp.models.response.NotificationType.WATER_LEVEL;
import static sanmateo.com.profileapp.models.response.NotificationType.WEATHER;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Notification> notifications;

    public NotificationsAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification,
                                                                        parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Notification notification = notifications.get(i);


        if (notification.getNotificationType().equals("WATER_LEVEL")) {
            if (notification.getWaterAlert().equals("Alert")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_water_green_40dp));
            } else if (notification.getWaterAlert().equals("Alarm")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_water_yellow_40dp));
            } else if (notification.getWaterAlert().equals("Critical")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_water_red_40dp));
            }
        } else if (notification.getNotificationType().equals("WEATHER")) {
            holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_weather_40dp));
        } else if (notification.getNotificationType().equals("INCIDENT")) {
            if (notification.getIncidentType().equals(FIRE)) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_fire_40dp));
            } else if (notification.getIncidentType().equals("FLOODING")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_flood_40dp));
            } else if (notification.getIncidentType().equals("TRAFFIC_ROAD")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_traffic_40dp));
            } else if (notification.getIncidentType().equals("MISCELLANEOUS")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_misc_40dp));
            } else if (notification.getIncidentType().equals("SOLID_WASTE")) {
                holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_waste_40dp));
            }
        } else if (notification.getNotificationType().equals("ANNOUNCEMENT")) {
            holder.ivIcon.setImageDrawable(getDrawable(context, R.drawable.ic_notif_misc_40dp));
        }

        holder.tvTitle.setText(notification.getTitle());

        holder.tvDescription.setText(notification.getDescription());

    }
}
