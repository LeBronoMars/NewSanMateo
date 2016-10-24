package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.singletons.PicassoSingleton;


/**
 * Created by rsbulanon on 6/30/16.
 */
public class FileToUploadAdapter extends RecyclerView.Adapter<FileToUploadAdapter.ImageViewHolder> {

    private ArrayList<File> files;
    private Context context;
    private OnSelectImageListener onSelectImageListener;

    public FileToUploadAdapter(final Context context, final ArrayList<File> files) {
        this.files = files;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_files_to_upload, parent, false);
        ImageViewHolder holder = new ImageViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        PicassoSingleton.getInstance().getPicasso().load(files.get(position)).fit()
                .noFade().centerCrop().into(holder.iv_image);
        holder.iv_remove.setOnClickListener(view -> {
            if (onSelectImageListener != null) {
                onSelectImageListener.onSelectedImage(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image) ImageView iv_image;
        @BindView(R.id.iv_remove) ImageView iv_remove;

        public ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public interface OnSelectImageListener {
        void onSelectedImage(final int position);
    }

    public void setOnSelectImageListener(OnSelectImageListener onSelectImageListener) {
        this.onSelectImageListener = onSelectImageListener;
    }
}
