package sanmateo.com.profileapp.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.models.response.Official;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 10/6/16.
 */

public class OfficialsAdapter extends RecyclerView.Adapter<OfficialsAdapter.ViewHolder> {
    private List<Official> officials;
    private Picasso picasso;
    private OnSelectOfficialListener onSelectOfficialListener;

    public OfficialsAdapter(List<Official> officials) {
        this.officials = officials;
        this.picasso = PicassoSingleton.getInstance().getPicasso();
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
        @BindView(R.id.cv_root) CardView cv_root;
        @BindView(R.id.civ_pic) CircleImageView civ_pic;
        @BindView(R.id.tv_official_name) TextView tv_official_name;
        @BindView(R.id.tv_position) TextView tv_position;
        @BindView(R.id.pb_load_image) ProgressBar pb_load_image;

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
        holder.tv_official_name.setText(official.getFirstName() + nickName + official.getLastName());
        holder.tv_position.setText(official.getPosition());
        holder.pb_load_image.setVisibility(View.VISIBLE);
        if (official.getPic() != null && !official.getPic().isEmpty()) {
            LogHelper.log("official", "position --> " + position + " pic url --> " + official.getPic());
            picasso.load(official.getPic())
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .fit()
                    .noFade()
                    .into(holder.civ_pic, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.pb_load_image.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            holder.pb_load_image.setVisibility(View.GONE);
                        }
                    });
        }
        holder.cv_root.setOnClickListener(v -> {
            if (onSelectOfficialListener != null) {
                onSelectOfficialListener.onSelectedOfficial(position, official);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnSelectOfficialListener {
        void onSelectedOfficial(final int position, final Official localOfficial);
    }

    public void setOnSelectOfficialListener(OnSelectOfficialListener onSelectOfficialListener) {
        this.onSelectOfficialListener = onSelectOfficialListener;
    }
}
