package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.models.others.Directory;

/**
 * Created by rsbulanon on 17/12/2017.
 */

public class NewDirectoriesAdapter extends RecyclerView.Adapter<NewDirectoriesAdapter.ViewHolder> {

    private ArrayList<Directory> directories;
    private Context context;
    private OnDirectoryActionListener onDirectoryActionListener;

    public NewDirectoriesAdapter(Context context, final ArrayList<Directory> directories) {
        this.context = context;
        this.directories = directories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.row_new_directory, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Directory directory = directories.get(position);

        holder.tvDirectoryName.setText(directory.name);

        holder.tvOptionsMenu.setOnClickListener(v -> {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(context, holder.tvOptionsMenu);
            //inflating menu from xml resource
            popup.inflate(R.menu.menu_directory);
            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_directory_call:
                        onDirectoryActionListener.call(directory.number);
                        break;
                    case R.id.menu_directory_email:
                        onDirectoryActionListener.email(directory.email);
                        break;
                }
                return false;
            });
            //displaying the popup
            popup.show();
        });

        holder.rlOptionsMenu.setOnClickListener(v -> holder.tvOptionsMenu.performClick());

    }

    @Override
    public int getItemCount() {
        return directories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDirectoryName)
        TextView tvDirectoryName;

        @BindView(R.id.tvOptionsMenu)
        TextView tvOptionsMenu;

        @BindView(R.id.rlOptionsMenu)
        RelativeLayout rlOptionsMenu;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDirectoryActionListener {
        void call(String number);

        void email(String email);
    }

    public void setOnDirectoryActionListener(OnDirectoryActionListener onDirectoryActionListener) {
        this.onDirectoryActionListener = onDirectoryActionListener;
    }
}
