package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * Created by rsbulanon on 10/2/16.
 */
public class ForgotPasswordDialogFragment extends DialogFragment {
    @BindView(R.id.et_email) EditText et_email;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnForgotPasswordListener onForgotPasswordListener;

    public static ForgotPasswordDialogFragment newInstance() {
        final ForgotPasswordDialogFragment fragment = new ForgotPasswordDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_forgot_password, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btn_send)
    public void manageLogin() {
        final String email = et_email.getText().toString().trim();
        
        if (email.isEmpty()) {
            activity.setError(et_email, AppConstants.WARN_FIELD_REQUIRED);
        } else if (!activity.isValidEmail(email)) {
            activity.setError(et_email, AppConstants.WARN_INVALID_EMAIL_FORMAT);
        } else {
            if (onForgotPasswordListener != null) {
                onForgotPasswordListener.onForgotPassword(email);
            }
        }
    }

    public interface OnForgotPasswordListener {
        void onForgotPassword(final String email);
    }

    public void setOnForgotPasswordListener(OnForgotPasswordListener onForgotPasswordListener) {
        this.onForgotPasswordListener = onForgotPasswordListener;
    }
}
