package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.DirectoriesActivity;


/**
 * Created by ctmanalo on 7/6/16.
 */
public class DirectoriesAdapter extends BaseExpandableListAdapter {

    private DirectoriesActivity activity;
    private LayoutInflater inflater;
    private ArrayList<String> header;
    private ArrayList<String> contacts;
    private ArrayList<String> email;
    private OnDirectoryActionListener onDirectoryActionListener;

    public DirectoriesAdapter(Context context, ArrayList<String> header, ArrayList<String> contacts,
                              ArrayList<String> email) {
        this.activity = (DirectoriesActivity) context;
        this.inflater = LayoutInflater.from(context);
        this.header = header;
        this.contacts = contacts;
        this.email = email;
    }

    @Override
    public int getGroupCount() {
        return header.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public String getGroup(int i) {
        return header.get(i);
    }

    @Override
    public String getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.header_directories, null, false);
        final TextView tv_header = (TextView)view.findViewById(R.id.tv_header);
        tv_header.setText(getGroup(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_directories, null, false);
        final TextView tv_contact = (TextView)view.findViewById(R.id.tv_contact);
        final TextView tv_email = (TextView)view.findViewById(R.id.tv_email);
        tv_contact.setText(contacts.get(i));
        if (email.size() > 0) {
            tv_email.setText(email.get(i));
            tv_email.setVisibility(View.VISIBLE);
        } else {
            tv_email.setVisibility(View.GONE);
        }
        tv_contact.setOnClickListener(view1 -> {
            if (onDirectoryActionListener != null) {
                onDirectoryActionListener.onCallDirectory(contacts.get(i));
            }
        });
        tv_email.setOnClickListener(view1 -> {
            if (onDirectoryActionListener != null) {
                onDirectoryActionListener.onSendEmailToDirectory(email.get(i));
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public interface OnDirectoryActionListener {
        void onCallDirectory(final String contactNo);
        void onSendEmailToDirectory(final String email);
    }

    public void setOnDirectoryActionListener(OnDirectoryActionListener onDirectoryActionListener) {
        this.onDirectoryActionListener = onDirectoryActionListener;
    }
}
