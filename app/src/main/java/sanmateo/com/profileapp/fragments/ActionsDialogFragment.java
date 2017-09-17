package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;

/**
 * Created by USER on 9/13/2017.
 */

public class ActionsDialogFragment extends DialogFragment{

    private View view;
    private Dialog mDialog;
    private Unbinder unbinder;
    private OnActionSelectedListener onActionSelectedListener;

    public static ActionsDialogFragment newInstance() {
        final ActionsDialogFragment fragment = new ActionsDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        getDialog().getWindow().setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_actions,
                null);
        unbinder = ButterKnife.bind(this, view);

        mDialog = new Dialog(getActivity());

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.ll_action_message)
    public void actionMessage() {
        onActionSelectedListener.onActionSelected(0);
        getDialog().dismiss();
    }

    @OnClick(R.id.ll_action_file_incident)
    public void actionFileIncident() {
        onActionSelectedListener.onActionSelected(1);
        getDialog().dismiss();
    }

    @OnClick(R.id.ll_action_call_pnp)
    public void callPnp() {
        onActionSelectedListener.onActionSelected(2);
        getDialog().dismiss();
    }

    @OnClick(R.id.ll_action_call_fire_department)
    public void callFireDepartment() {
        onActionSelectedListener.onActionSelected(3);
        getDialog().dismiss();
    }

    @OnClick(R.id.ll_action_call_icco)
    public void callIcco() {
        onActionSelectedListener.onActionSelected(4);
        getDialog().dismiss();
    }

    @OnClick(R.id.ll_action_call_rescue)
    public void callRescue() {
        onActionSelectedListener.onActionSelected(5);
        getDialog().dismiss();
    }

    @OnClick(R.id.ll_action_send_sos)
    public void sendSos() {
        onActionSelectedListener.onActionSelected(6);
        getDialog().dismiss();
    }

    public void setOnActionSelectedListener(OnActionSelectedListener onActionSelectedListener) {
        this.onActionSelectedListener = onActionSelectedListener;
    }

    public interface OnActionSelectedListener {
        void onActionSelected(int position);
    }
}
