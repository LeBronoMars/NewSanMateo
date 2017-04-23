package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
 * Created by avinnovz on 4/23/17.
 */

public class EulaDialogFragment extends DialogFragment{

    private Unbinder unbinder;
    private EulaListener eulaListener;

    public static EulaDialogFragment newInstance() {
        final EulaDialogFragment dialogFragment = new EulaDialogFragment();
        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_eula, null);
        unbinder = ButterKnife.bind(this, view);

        final Dialog mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btn_accept)
    public void acceptEula() {
        eulaListener.onAccepted();
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_decline)
    public void declineEula() {
        getDialog().dismiss();
    }

    public interface EulaListener {
        void onAccepted();
    }

    public void setEulaListener(EulaListener eulaListener) {
        this.eulaListener = eulaListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
