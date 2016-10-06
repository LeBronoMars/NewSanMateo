package sanmateo.com.profileapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.models.response.Official;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 10/6/16.
 */

public class OfficialsAdapter extends RecyclerView.Adapter<OfficialsAdapter.ViewHolder> {

    private List<Official> officials;
    private PicassoSingleton picassoSingleton;


    public OfficialsAdapter(List<Official> officials) {
        this.officials = officials;
        picassoSingleton = PicassoSingleton.getInstance();
    }

    @Override
    public int getItemCount() {
        return officials.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_official,
                parent, false);
        return new  ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_pic) CircleImageView civPic;
        @BindView(R.id.tv_official_name) TextView tvOfficialName;
        @BindView(R.id.tv_position) TextView tvPosition;
        @BindView(R.id.pb_load_image) ProgressBar pbLoadImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Official official = officials.get(position);
        final String nickName = official.getNickName().isEmpty()
                ? " " : " '"+official.getNickName()+"' ";
        holder.tvOfficialName.setText(official.getFirstName() + nickName + official.getLastName());
        holder.tvPosition.setText(official.getPosition());
        holder.pbLoadImage.setVisibility(View.VISIBLE);
        picassoSingleton.getPicasso().load(official.getPic())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.civPic, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pbLoadImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.pbLoadImage.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
