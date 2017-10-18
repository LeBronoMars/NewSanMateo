package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.response.Incident;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by USER on 10/16/2017.
 */

public class IncidentReportsAdapter extends RecyclerView.Adapter<IncidentReportsAdapter.ViewHolder>{

    private ArrayList<Incident> incidents;
    private BaseActivity activity;
    private Context context;
    private OnIncidentReportListener onIncidentReportListener;

    public IncidentReportsAdapter(Context context, ArrayList<Incident> incidents) {
        this.context = context;
        this.incidents = incidents;
        this.activity = (BaseActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_incident_report,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Incident incident = incidents.get(position);
        try {
            final Date dateReported = activity.getDateFormatter().parse(incident.getIncidentDateReported());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            holder.tvIncidentTime.setText(activity.getTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!incident.getImages().isEmpty()) {
            PicassoSingleton.getInstance().getPicasso().load(incident.getImages())
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .fit()
                    .into(holder.ivIncident);
        }
        holder.tvIncidentSummary.setText(incident.getIncidentDescription());

        holder.tvReadMoreIncident.setOnClickListener(view -> {
            if (onIncidentReportListener != null) {
                onIncidentReportListener.onIncidentSelected(incident);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_read_more_incident)
        TextView tvReadMoreIncident;

        @BindView(R.id.tv_incident_summary)
        TextView tvIncidentSummary;

        @BindView(R.id.tv_incident_time)
        TextView tvIncidentTime;

        @BindView(R.id.iv_incident)
        ImageView ivIncident;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnIncidentReportListener {
        void onIncidentSelected(Incident incident);
    }

    public void setOnIncidentReportListener(OnIncidentReportListener onIncidentReportListener) {
        this.onIncidentReportListener = onIncidentReportListener;
    }
}
