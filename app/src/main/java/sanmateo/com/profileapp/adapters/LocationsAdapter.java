package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.MapActivity;
import sanmateo.com.profileapp.models.others.Location;

/**
 * Created by ctmanalo on 7/19/16.
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder>{

    private ArrayList<Location> locations;
    private Context context;
    private MapActivity activity;

    public LocationsAdapter(final Context context, final ArrayList<Location> locations) {
        this.context = context;
        this.activity = (MapActivity) context;
        this.locations = locations;
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_locations, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_location) LinearLayout ll_location;
        @BindView(R.id.rl_view_image) RelativeLayout rl_view_image;
        @BindView(R.id.tv_location_name) TextView tv_location_name;
        @BindView(R.id.tv_category) TextView tv_category;
        @BindView(R.id.tv_location_address) TextView tv_location_address;
        @BindView(R.id.tv_contact_no) TextView tv_contact_no;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Location location = locations.get(position);
        holder.tv_location_name.setText(location.getLocationName());
        holder.tv_category.setText(location.getCategory());
        holder.tv_location_address.setText(location.getLocationAddress());
        holder.tv_contact_no.setText(location.getContactNo());
        holder.rl_view_image.setOnClickListener(view -> {
            if (!location.getImageUrl().equals("")) {
                activity.showImage(location.getImageUrl());
            } else {
                activity.showToast("Image not yet available...");
            }
        });
        holder.ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location.getLatLng() != null) {
                    activity.focusOnMap(location.getLatLng());
                } else {
                    activity.showToast("Cannot find location on map...");
                }
            }
        });
    }
}
