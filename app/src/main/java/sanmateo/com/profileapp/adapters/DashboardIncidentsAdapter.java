package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by USER on 9/17/2017.
 */

public class DashboardIncidentsAdapter extends RecyclerView.Adapter<DashboardIncidentsAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Incident> incidents;
    private BaseActivity activity;

    public DashboardIncidentsAdapter(Context context, ArrayList<Incident> incidents) {
        this.context = context;
        this.activity = (BaseActivity) context;
        this.incidents = incidents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dashboard_incidents, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Incident incident = incidents.get(position);
        try {
            final Date dateReported = activity.getDateFormatter().parse(incident.getIncidentDateReported());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            holder.tvIncidentTime.setText(activity.getTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvIncidentTitle.setText(incident.getIncidentType());
        if (!incident.getImages().isEmpty()) {
            PicassoSingleton.getInstance().getPicasso().load(incident.getImages())
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .fit()
                    .into(holder.ivIncident);
        }
        holder.tvIncidentSummray.setText(incident.getIncidentDescription());

        holder.tvReadMoreIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) context).showToast("read more: " + holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_incident)
        RelativeLayout rlIncident;

        @BindView(R.id.tv_read_more_incident)
        TextView tvReadMoreIncident;

        @BindView(R.id.tv_incident_summary)
        TextView tvIncidentSummray;

        @BindView(R.id.tv_incident_title)
        TextView tvIncidentTitle;

        @BindView(R.id.tv_incident_time)
        TextView tvIncidentTime;

        @BindView(R.id.iv_incident)
        ImageView ivIncident;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
