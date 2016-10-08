package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.HotlinesActivity;
import sanmateo.com.profileapp.customviews.ContactView;
import sanmateo.com.profileapp.customviews.OverflowLayout;
import sanmateo.com.profileapp.models.others.Hotlines;

/**
 * Created by rsbulanon on 5/14/15.
 */
public class HotlinesAdapter extends RecyclerView.Adapter<HotlinesAdapter.ContactHolder> {

    private ArrayList<Hotlines> hotlines;
    private Context context;
    private HotlinesActivity activity;
    private LayoutInflater inflater;

    public HotlinesAdapter(Context context, ArrayList<Hotlines> hotlines) {
        this.context = context;
        this.hotlines = hotlines;
        this.inflater = LayoutInflater.from(context);
        this.activity = (HotlinesActivity) context;
    }

    @Override
    public int getItemCount() {
        return hotlines.size();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card_hotline, parent, false);
        ContactHolder holder = new ContactHolder(v);
        return holder;
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {
        CardView cv_hot_line;
        TextView tv_header;
        TextView tv_sub_header;
        OverflowLayout ll_over_flow;

        ContactHolder(View view) {
            super(view);
            cv_hot_line = (CardView) view.findViewById(R.id.cv_hot_line);
            tv_header = (TextView) view.findViewById(R.id.tv_header);
            tv_sub_header = (TextView) view.findViewById(R.id.tv_sub_header);
            ll_over_flow = (OverflowLayout) view.findViewById(R.id.ll_over_flow);
        }
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int i) {
        holder.tv_header.setText(hotlines.get(i).getHeader());
        holder.tv_sub_header.setText(hotlines.get(i).getSubHeader());
        ArrayList<String> no = hotlines.get(i).getNumbers();
        for (String s : no) {
            final ContactView contactView = new ContactView(context, s);
            holder.ll_over_flow.addView(contactView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}