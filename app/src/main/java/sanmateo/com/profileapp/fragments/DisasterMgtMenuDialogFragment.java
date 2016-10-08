package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.DisasterMenuAdapter;
import sanmateo.com.profileapp.base.BaseActivity;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class DisasterMgtMenuDialogFragment extends DialogFragment {

    @BindView(R.id.tv_header) TextView tv_header;
    @BindView(R.id.lv_disaster_menu) ListView lv_disaster_menu;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnSelectDisasterMenuListener onSelectDisasterMenuListener;
    private String header;
    private ArrayList<String> menu;

    public static DisasterMgtMenuDialogFragment newInstance(final String header, final ArrayList<String> menu) {
        final DisasterMgtMenuDialogFragment fragment = new DisasterMgtMenuDialogFragment();
        fragment.header = header;
        fragment.menu = menu;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
        Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_disaster_mgmt, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());
        tv_header.setText(header);
        final DisasterMenuAdapter adapter = new DisasterMenuAdapter(getActivity(),menu);
        lv_disaster_menu.setAdapter(adapter);
        lv_disaster_menu.setOnItemClickListener((adapterView, view1, i, l) -> onSelectDisasterMenuListener.onSelectedMenu(i));

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btn_close)
    public void cancel() {
        if (onSelectDisasterMenuListener != null) {
            onSelectDisasterMenuListener.onClose();
        }
    }

    public interface OnSelectDisasterMenuListener {
        void onSelectedMenu(final int position);
        void onClose();
    }

    public void setOnSelectDisasterMenuListener(OnSelectDisasterMenuListener onSelectDisasterMenuListener) {
        this.onSelectDisasterMenuListener = onSelectDisasterMenuListener;
    }
}
