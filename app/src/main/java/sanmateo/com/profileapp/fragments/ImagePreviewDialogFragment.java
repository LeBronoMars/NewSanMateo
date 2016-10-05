package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.activities.MapActivity;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 7/20/16.
 */
public class ImagePreviewDialogFragment extends DialogFragment {

    @BindView(R.id.iv_location_image) ImageView iv_location_image;

    private View view;
    private Dialog dialog;
    private MapActivity mapActivity;
    private String imageUrl;

    public static ImagePreviewDialogFragment newInstance(String imageUrl) {
        ImagePreviewDialogFragment fragment = new ImagePreviewDialogFragment();
        fragment.imageUrl = imageUrl;
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_image_preview, null);
        ButterKnife.bind(this, view);
        mapActivity = (MapActivity)getActivity();
        initImage();
        dialog = new Dialog(mapActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    private void initImage() {
        PicassoSingleton.getInstance().getPicasso().load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .centerCrop()
                .into(iv_location_image);
    }

    @OnClick(R.id.btn_close)
    public void closeDialog() {
        dialog.dismiss();
    }
}
