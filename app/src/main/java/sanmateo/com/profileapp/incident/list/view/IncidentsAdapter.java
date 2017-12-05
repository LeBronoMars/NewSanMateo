package sanmateo.com.profileapp.incident.list.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.incident.usecase.Incident;
import sanmateo.com.profileapp.util.TimeUtils;
import sanmateo.com.profileapp.util.glide.GlideImageLoader;


import static android.view.LayoutInflater.from;
import static sanmateo.com.profileapp.util.TimeUtils.fromIso8601;
import static sanmateo.com.profileapp.util.TimeUtils.toHourMinute;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.ViewHolder> {

    private ArrayList<Incident> incidents = new ArrayList<>();

    private GlideImageLoader glideImageLoader;

    private OnReadMoreListener onReadMoreListener;

    public IncidentsAdapter(GlideImageLoader glideImageLoader, ArrayList<Incident> incidents) {
        this.glideImageLoader = glideImageLoader;
        this.incidents = incidents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = from(parent.getContext()).inflate(R.layout.item_incident_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Incident incident = incidents.get(position);

        glideImageLoader.loadImage(incident.images, holder.itemIncidentImage);

        holder.itemIncidentSummary.setText(incident.incidentDescription);

        holder.itemIncidentTime.setText(toHourMinute(fromIso8601(incident.incidentDateReported)));

        holder.itemIncidentTitle.setText(incident.incidentType);

        holder.itemIncidentReadMore.setOnClickListener(v -> {
            if (null != onReadMoreListener) {
                onReadMoreListener.onReadMore(incident);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_incident_image)
        ImageView itemIncidentImage;

        @BindView(R.id.item_incident_time)
        TextView itemIncidentTime;

        @BindView(R.id.item_incident_title)
        TextView itemIncidentTitle;

        @BindView(R.id.item_incident_summary)
        TextView itemIncidentSummary;

        @BindView(R.id.item_incident_read_more)
        TextView itemIncidentReadMore;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnReadMoreListener {
        void onReadMore(Incident incident);
    }

    public void setOnReadMoreListener(OnReadMoreListener onReadMoreListener) {
        this.onReadMoreListener = onReadMoreListener;
    }
}
