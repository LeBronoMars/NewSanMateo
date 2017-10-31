package sanmateo.com.profileapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.helpers.PicassoHelper;
import sanmateo.com.profileapp.models.others.Location;

public class NewMapsAdapter extends RecyclerView.Adapter<NewMapsAdapter.ViewHolder> {

    private ArrayList<Location> locations;

    private OnSelectLocationListener onSelectLocationListener;

    public NewMapsAdapter(final ArrayList<Location> locations) {
        this.locations = locations;
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.row_new_map, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_location_name)
        TextView tvLocationName;

        @BindView(R.id.tv_location_address)
        TextView tvLocationAddress;

        @BindView(R.id.iv_location_pic)
        ImageView ivLocationPic;

        @BindView(R.id.iv_call)
        ImageView ivCall;

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Location location = locations.get(i);

        holder.tvLocationName.setText(location.getLocationName());
        holder.tvLocationAddress.setText(location.getLocationAddress());
        if (null != location.getImageUrl() && !location.getImageUrl()
                                                       .isEmpty()) {
            PicassoHelper.loadImageFromURL(location.getImageUrl(),
                                           holder.ivLocationPic,
                                           holder.progressBar);
        } else {
            holder.progressBar.setVisibility(View.GONE);
            PicassoHelper.loadImageFromDrawable(R.drawable.no_location_image_available,
                                                holder.ivLocationPic);
        }

        holder.itemView.setOnClickListener(view -> {
            onSelectLocationListener.onSelect(location);
        });

        holder.ivCall.setOnClickListener(view -> {
            onSelectLocationListener.onCall(location.getContactNo());
        });
    }

    public interface OnSelectLocationListener {
        void onSelect(Location location);

        void onCall(String phoneNumber);
    }

    public void setOnSelectLocationListener(OnSelectLocationListener onSelectLocationListener) {
        this.onSelectLocationListener = onSelectLocationListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
