package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;

/**
 * Created by rsbulanon on 10/4/16.
 */

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private OnChangeProfilePicListener onChangeProfilePicListener;

    public static CustomBottomSheetDialogFragment newInstance() {
        return new CustomBottomSheetDialogFragment();
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);

        final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                ((View) contentView.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @OnClick(R.id.tv_open_camera)
    public void openCamera() {
        if (onChangeProfilePicListener != null) {
            onChangeProfilePicListener.onSelectPictureVia("via camera");
        }
    }

    @OnClick(R.id.tv_open_gallery)
    public void openGallery() {
        if (onChangeProfilePicListener != null) {
            onChangeProfilePicListener.onSelectPictureVia("via gallery");
        }
    }

    public interface OnChangeProfilePicListener {
        void onSelectPictureVia(final String action);
    }

    public void setOnChangeProfilePicListener(OnChangeProfilePicListener onChangeProfilePicListener) {
        this.onChangeProfilePicListener = onChangeProfilePicListener;
    }
}
