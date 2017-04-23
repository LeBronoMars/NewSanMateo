package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.models.others.CustomMenu;

/**
 * Created by avinnovz on 4/23/17.
 */

public class CustomNavMenuAdapter extends BaseAdapter {

    @BindView(R.id.iv_icon)
    ImageView iv_icon;

    @BindView(R.id.tv_menu)
    TextView tv_menu;

    @BindView(R.id.tv_edit)
    TextView tv_edit;

    private Context context;
    private ArrayList<CustomMenu> menuItems;
    private CustomMenuListener customMenuListener;

    public CustomNavMenuAdapter(final Context context, final ArrayList<CustomMenu> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public CustomMenu getItem(int i) {
        return menuItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = LayoutInflater.from(context).inflate(R.layout.row_nav_menu, null);
        ButterKnife.bind(this, v);

        final CustomMenu customMenu = getItem(i);
        iv_icon.setImageDrawable(ContextCompat.getDrawable(context, customMenu.getMenuIcon()));
        tv_menu.setText(customMenu.getMenuTitle());
        if (customMenu.isEditable()) {
            tv_edit.setVisibility(View.VISIBLE);
            tv_edit.setOnClickListener(view1 -> customMenuListener.onEditSelected());
        } else {
            tv_edit.setVisibility(View.GONE);
        }
        return v;
    }

    public interface CustomMenuListener {
        void onEditSelected();
    }

    public void setCustomMenuListener(CustomMenuListener customMenuListener) {
        this.customMenuListener = customMenuListener;
    }
}
