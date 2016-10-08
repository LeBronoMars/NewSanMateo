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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.models.response.Gallery;
import sanmateo.com.profileapp.singletons.PicassoSingleton;

/**
 * Created by ctmanalo on 8/12/16.
 */
public class GalleryDetailFragment extends DialogFragment {
    @BindView(R.id.iv_gallery) ImageView iv_gallery;
    @BindView(R.id.pb_load_image) ProgressBar pb_load_image;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_description) TextView tv_description;
    private View mView;
    private Dialog mDialog;
    private Gallery gallery;

    public static GalleryDetailFragment newInstance(final Gallery gallery) {
        final GalleryDetailFragment fragment = new GalleryDetailFragment();
        fragment.gallery = gallery;
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
        mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_gallery_detail, null);
        ButterKnife.bind(this, mView);
        initDetails();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    private void initDetails() {
        initPhoto();
        tv_title.setText(gallery.getTitle());
        tv_description.setText(gallery.getDescription());
    }

    private void initPhoto() {
        pb_load_image.setVisibility(View.VISIBLE);
        PicassoSingleton.getInstance().getPicasso().load(gallery.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .into(iv_gallery, new Callback() {
                    @Override
                    public void onSuccess() {
                        pb_load_image.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pb_load_image.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick(R.id.rlClose)
    public void closeDialog() {
        mDialog.dismiss();
    }
}
