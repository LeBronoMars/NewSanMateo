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
 * Created by USER on 10/14/2017.
 */

public class IncidentAddImageFragment extends DialogFragment {

    private Unbinder unbinder;
    private OnAddIncidentImageListener onAddIncidentImageListener;

    public static IncidentAddImageFragment newInstance() {
        return new IncidentAddImageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setGravity(Gravity.TOP | Gravity.LEFT);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_add_image, null);
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

    @OnClick(R.id.ll_take_photo)
    public void takePhoto() {
        if (onAddIncidentImageListener != null) {
            onAddIncidentImageListener.captureImage();
        }
    }

    @OnClick(R.id.ll_select_from_gallery)
    public void selectPhoto() {
        if (onAddIncidentImageListener != null) {
            onAddIncidentImageListener.addImageFromGallery();
        }
    }

    public interface OnAddIncidentImageListener {
        void captureImage();
        void addImageFromGallery();
    }

    public void setOnAddIncidentImageListener(OnAddIncidentImageListener onAddIncidentImageListener) {
        this.onAddIncidentImageListener = onAddIncidentImageListener;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
