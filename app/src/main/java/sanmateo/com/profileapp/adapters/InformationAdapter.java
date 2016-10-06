package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.InformationActivity;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by ctmanalo on 10/5/16.
 */

public class InformationAdapter extends BaseAdapter {

    @BindView(R.id.iv_icon) ImageView ivIcon;
    @BindView(R.id.tv_header) TextView tvHeader;
    private String[] header = new String[] {"History", "Officials", "Government"};
    private int[] icons = new int[] {R.drawable.history, R.drawable.officials, R.drawable.government};
    private BaseActivity activity;

    public InformationAdapter(Context context) {
        this.activity = (InformationActivity) context;
    }

    @Override
    public int getCount() {
        return header.length;
    }

    @Override
    public String getItem(int i) {
        return header[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.row_information, null, false);
        ButterKnife.bind(this, view);
        ivIcon.setImageResource(icons[i]);
        tvHeader.setText(header[i]);
        return view;
    }
}
