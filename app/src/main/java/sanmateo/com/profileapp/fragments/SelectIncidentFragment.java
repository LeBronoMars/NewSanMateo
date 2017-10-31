package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
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
 * Created by USER on 10/15/2017.
 */

public class SelectIncidentFragment extends DialogFragment {

    private Unbinder unbinder;
    private OnIncidentTypeListener onIncidentTypeListener;

    public static SelectIncidentFragment newInstance() {
        return new SelectIncidentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_incident_type, null);
        unbinder = ButterKnife.bind(this, view);

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @OnClick(R.id.ll_traffic)
    public void trafficIncident() {
        if (onIncidentTypeListener != null) {
            onIncidentTypeListener.onIncidentSelected(0);
        }
    }

    @OnClick(R.id.ll_solid_waste)
    public void wasteIncident() {
        if (onIncidentTypeListener != null) {
            onIncidentTypeListener.onIncidentSelected(1);
        }
    }

    @OnClick(R.id.ll_flooding)
    public void floodIncident() {
        if (onIncidentTypeListener != null) {
            onIncidentTypeListener.onIncidentSelected(2);
        }
    }

    @OnClick(R.id.ll_fire)
    public void fireIncident() {
        if (onIncidentTypeListener != null) {
            onIncidentTypeListener.onIncidentSelected(3);
        }
    }

    @OnClick(R.id.ll_miscellaneous)
    public void miscellaneousIncident() {
        if (onIncidentTypeListener != null) {
            onIncidentTypeListener.onIncidentSelected(4);
        }
    }

    public interface OnIncidentTypeListener {
        void onIncidentSelected(final int index);
    }

    public void setOnIncidentTypeListener(OnIncidentTypeListener onIncidentTypeListener) {
        this.onIncidentTypeListener = onIncidentTypeListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
