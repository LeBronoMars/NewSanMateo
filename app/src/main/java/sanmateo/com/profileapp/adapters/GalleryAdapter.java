package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.response.Gallery;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 8/5/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Gallery> galleries;
    private BaseActivity baseActivity;
    private int screenWidth;
    private OnGalleryClickListener onGalleryClickListener;

    public GalleryAdapter(Context context, ArrayList<Gallery> galleries) {
        this.context = context;
        this.galleries = galleries;
        this.baseActivity = (BaseActivity) context;
        WindowManager wm = (WindowManager) baseActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_gallery, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_photo) ImageView iv_photo;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.pb_load_image) ProgressBar pb_load_image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Gallery gallery = galleries.get(position);

        holder.tv_title.setText(gallery.getTitle());
        holder.pb_load_image.setVisibility(View.VISIBLE);
        PicassoSingleton.getInstance().getPicasso().load(gallery.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .into(holder.iv_photo, new Callback() {
            @Override
            public void onSuccess() {
                holder.pb_load_image.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.pb_load_image.setVisibility(View.GONE);
            }
        });
        holder.iv_photo.setOnClickListener(view -> onGalleryClickListener.onSelectedGallery(gallery));
    }

    public interface OnGalleryClickListener {
        void onSelectedGallery(final Gallery gallery);
    }

    public void setOnGalleryClickListener(OnGalleryClickListener onGalleryClickListener) {
        this.onGalleryClickListener = onGalleryClickListener;
    }
}
