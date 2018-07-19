package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.CustomBottomSheetDialogFragment;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.PicassoHelper;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.interfaces.OnS3UploadListener;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.models.response.GenericMessage;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static sanmateo.com.profileapp.enums.ApiAction.PUT_CHANGE_PROFILE_PIC;
import static sanmateo.com.profileapp.enums.ApiAction.PUT_UPDATE_USER;

/**
 * Created by rsbulanon on 17/12/2017.
 */

public class UpdateProfileActivity extends BaseActivity implements OnApiRequestListener,
                                                                   OnS3UploadListener {

    private static final int SELECT_IMAGE = 1;

    private static final int CAPTURE_IMAGE = 2;

    @BindView(R.id.civProfilePic)
    CircleImageView civProfilePic;

    @BindView(R.id.iv_save)
    ImageView ivSave;

    @BindView(R.id.iv_update)
    ImageView ivUpdate;

    @BindView(R.id.tvFullName)
    TextView tvFullName;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.etFirstName)
    EditText etFirstName;

    @BindView(R.id.etLastName)
    EditText etLastName;

    @BindView(R.id.llFullName)
    LinearLayout llFullName;

    @BindView(R.id.llMemberSince)
    LinearLayout llMemberSince;

    @BindView(R.id.tvMemberSince)
    TextView tvMemberSince;

    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;

    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.pbProfilePic)
    ProgressBar pbProfilePic;

    private Unbinder unbinder;

    private AuthResponse currentUser;

    private String uploadToBucket;

    private Uri fileUri;

    private File fileToUpload;

    private ApiRequestHelper apiRequestHelper;

    private String newFirstName;

    private String newLastName;

    private String contactNo;

    private String address;

    private RealmHelper<AuthResponse> realmHelper = new RealmHelper<>(AuthResponse.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        unbinder = ButterKnife.bind(this);

        setStatusBarColor();

        currentUser = CurrentUserSingleton.getInstance().getCurrentUser();

        if (currentUser.getPicUrl() != null && !currentUser.getPicUrl().isEmpty()) {
            PicassoHelper.loadImageFromURL(currentUser.getPicUrl(),
                    R.drawable.ic_avatar_placeholder_64dp,
                    pbProfilePic, civProfilePic);
        }

        tvFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        tvEmail.setText(currentUser.getEmail());

        etFirstName.setText(currentUser.getFirstName());
        etLastName.setText(currentUser.getLastName());

        // compute member since
        tvMemberSince.setText(computeMembershipDuration(currentUser.getCreatedAt()));

        if (currentUser.getContactNo() != null && !currentUser.getContactNo().isEmpty()) {
            etMobileNumber.setText(currentUser.getContactNo());
        } else {
            etMobileNumber.setText("No mobile no. provided");
        }
        etMobileNumber.setEnabled(false);

        if (currentUser.getAddress() != null && !currentUser.getAddress().isEmpty()) {
            etAddress.setText(currentUser.getAddress());
        } else {
            etAddress.setText("No address provided");
        }
        etAddress.setEnabled(false);

        apiRequestHelper = new ApiRequestHelper(this);

        initAmazonS3Helper(this);
    }

    private String computeMembershipDuration(String membershipDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            return sdf.format(getDateFormatter().parse(membershipDate));
        } catch (ParseException e) {
            return "";
        }
    }

    @OnClick(R.id.iv_update)
    public void editInfo() {
        ivUpdate.setVisibility(GONE);
        ivSave.setVisibility(VISIBLE);
        llFullName.setVisibility(VISIBLE);
        llMemberSince.setVisibility(GONE);

        etFirstName.requestFocus();
        etFirstName.setSelection(currentUser.getFirstName().length());

        etMobileNumber.setEnabled(true);
        etAddress.setEnabled(true);
    }

    @OnClick(R.id.iv_cancel)
    public void cancel() {
        finish();
        animateToRight(this);
    }

    @OnClick(R.id.iv_save)
    public void saveInfo() {
        if (hasNoChanges()) {
            showSnackbar(etAddress, "No changes detected.");
        } else {
            if (!isNetworkAvailable()) {
                showConfirmDialog(null, "Connection Error",
                                  AppConstants.WARN_CONNECTION_NEW, "Close", null, null);
            } else {
                apiRequestHelper.updateUser(CurrentUserSingleton.getInstance().getCurrentUser().getToken(),
                    CurrentUserSingleton.getInstance().getCurrentUser().getId(), newFirstName,
                                            newLastName, contactNo, address);
            }
        }
    }

    private boolean hasNoChanges() {
        newFirstName = etFirstName.getText().toString();
        newLastName = etLastName.getText().toString();
        contactNo = etMobileNumber.getText().toString();
        address = etAddress.getText().toString();

        AuthResponse authResponse = CurrentUserSingleton.getInstance().getCurrentUser();

        return authResponse.getFirstName().equalsIgnoreCase(newFirstName) &&
                   authResponse.getLastName().equalsIgnoreCase(newLastName) &&
                   authResponse.getContactNo().equalsIgnoreCase(contactNo) &&
                   authResponse.getAddress().equalsIgnoreCase(address);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.civProfilePic)
    public void changeProfilePic() {
        showChangeProfilePicMenu();
    }

    private void uploadFileToAmazon() {
        /** delete previous profile pic from s3 if it's not the default profile pic using gravatar */
        if (!CurrentUserSingleton.getInstance().getCurrentUser().getPicUrl()
                                 .contains("http://www.gravatar.com/avatar/")) {
            deleteImage(AppConstants.BUCKET_ROOT,
                        CurrentUserSingleton.getInstance().getCurrentUser().getPicUrl());
        }
        uploadImageToS3(uploadToBucket, fileToUpload, 1, 1);
    }

    private void showChangeProfilePicMenu() {
        uploadToBucket = AppConstants.BUCKET_PROFILE_PIC;
        final CustomBottomSheetDialogFragment bottomSheetDialogFragment = CustomBottomSheetDialogFragment.newInstance();
        bottomSheetDialogFragment.setOnChangeProfilePicListener(action -> {
            bottomSheetDialogFragment.dismiss();
            switch (action) {
                case "via gallery":
                    final Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
                    break;
                case "via camera":
                    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        fileToUpload = createImageFile();
                        fileUri = Uri.fromFile(fileToUpload);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                    } catch (Exception ex) {
                        Log.d("app", "exception --> " + ex.getMessage());
                        showConfirmDialog("", "Capture Image",
                                          "We can't get your image. Please try again.", "Close", "", null);
                    }
                    break;
            }
        });
        bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom sheet");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                fileToUpload = getFile(data.getData(), UUID.randomUUID().toString() + ".png");
                uploadFileToAmazon();
            } else if (requestCode == CAPTURE_IMAGE) {
                fileToUpload = rotateBitmap(fileUri.getPath());
                uploadFileToAmazon();
            }
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.PUT_UPDATE_USER)) {
            showCustomProgress("Updating...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        dismissCustomProgress();

        if (action.equals(PUT_CHANGE_PROFILE_PIC)) {
            final GenericMessage genericMessage = (GenericMessage) result;
            showToast("You have successfully changed your profile pic");

            /** save new profile pic url */
            fileToUpload = null;
            fileUri = null;

            final RealmHelper<AuthResponse> realmHelper = new RealmHelper<>(AuthResponse.class);
            realmHelper.openRealm();
            CurrentUserSingleton.getInstance().getCurrentUser()
                                .setPicUrl(genericMessage.getMessage());

            realmHelper.update(CurrentUserSingleton.getInstance().getCurrentUser());
            realmHelper.commitTransaction();

            if (currentUser.getPicUrl() != null && !currentUser.getPicUrl().isEmpty()) {
                PicassoHelper.loadImageFromURL(currentUser.getPicUrl(),
                        R.drawable.ic_avatar_placeholder_64dp,
                        pbProfilePic, civProfilePic);
            }
        } else if (action.equals(PUT_UPDATE_USER)) {
            AuthResponse authResponse = (AuthResponse) result;

            realmHelper.replaceInto(authResponse);
            CurrentUserSingleton.getInstance().setCurrentUser(authResponse);

            finish();
            animateToRight(this);

            Toast.makeText(this, "Info successfully updated", LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
        Log.d("app", "errooorrrr -->  " + t.getMessage());
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_PROFILE_PIC)) {
            /** upload new pic url */
            apiRequestHelper.changeProfilePic(CurrentUserSingleton.getInstance().getCurrentUser().getToken()
                , CurrentUserSingleton.getInstance().getCurrentUser().getId(), imageUrl);
        }
        uploadToBucket = "";
    }
}
