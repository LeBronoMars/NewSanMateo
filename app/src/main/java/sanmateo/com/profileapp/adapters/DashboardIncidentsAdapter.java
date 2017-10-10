package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by USER on 9/17/2017.
 */

public class DashboardIncidentsAdapter extends RecyclerView.Adapter<DashboardIncidentsAdapter.ViewHolder>{

    private Context context;

    public DashboardIncidentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dashboard_incidents, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvIncidentTime.setText("9:45 am");
        holder.tvIncidentTitle.setText("Traffic Report");
        int imageDrawable = R.drawable.image_1;
        switch (position) {
            case 0:
                imageDrawable = R.drawable.image_1;
                break;
            case 1:
                imageDrawable = R.drawable.image_2;
                break;
            case 2:
                imageDrawable = R.drawable.image_3;
                break;
        }
        holder.ivIncident.setImageDrawable(ContextCompat.getDrawable(context, imageDrawable));
        holder.tvIncidentSummray.setText("One lane along General Luna is closed due to an accident involving a UV Express anddsadsadsadsadsadsadsadasdsasadasdas");

        holder.tvReadMoreIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) context).showToast("read more: " + holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
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
