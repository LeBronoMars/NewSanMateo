package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.AppConstants;

/**
 * Created by ctmanalo on 7/13/16.
 */
public class ChangePasswordDialogFragment extends DialogFragment {

    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnChangePasswordListener onChangePasswordListener;

    @BindView(R.id.et_old_password) EditText et_old_password;
    @BindView(R.id.et_new_password) EditText et_new_password;
    @BindView(R.id.et_confirm_password) EditText et_confirm_password;

    public static ChangePasswordDialogFragment newInstance() {
        final ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_change_password, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick({R.id.btnChangePassword, R.id.btnCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangePassword:
                if (!activity.isNetworkAvailable()) {
                    activity.showToast(AppConstants.WARN_CONNECTION);
                } else if (et_old_password.getText().toString().isEmpty()) {
                    activity.setError(et_old_password, AppConstants.WARN_FIELD_REQUIRED);
                } else if (et_new_password.getText().toString().isEmpty()) {
                    activity.setError(et_new_password, AppConstants.WARN_FIELD_REQUIRED);
                } else if (!et_new_password.getText().toString().equals(et_confirm_password.getText().toString())) {
                    activity.setError(et_confirm_password, AppConstants.WARN_PASSWORD_NOT_MATCH);
                } else {
                    String oldPassword = et_old_password.getText().toString();
                    String newPassword = et_new_password.getText().toString();
                    onChangePasswordListener.onConfirm(oldPassword, newPassword);
                    mDialog.dismiss();
                }
                break;
            case R.id.btnCancel:
                mDialog.dismiss();
                break;
        }
    }

    public interface OnChangePasswordListener {
        void onConfirm(final String oldPassword, final String newPassword);
    }

    public void setOnChangePasswordListener(OnChangePasswordListener onChangePasswordListener) {
        this.onChangePasswordListener = onChangePasswordListener;
    }
}
