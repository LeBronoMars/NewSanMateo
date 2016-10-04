package sanmateo.com.profileapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.FileToUploadAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.LogHelper;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class FileIncidentReportDialogFragment extends DialogFragment {

    @BindView(R.id.et_incident_description) EditText et_incident_description;
    @BindView(R.id.et_incident_location) EditText et_incident_location;
    @BindView(R.id.spnr_incident_type) Spinner spnr_incident_type;
    @BindView(R.id.rv_images) RecyclerView rv_images;
    @BindView(R.id.ll_add_photo) LinearLayout ll_add_photo;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private ArrayList<File> filesToUpload = new ArrayList<>();
    private OnFileIncidentReportListener onFileIncidentReportListener;
    private Uri fileUri;
    private File fileToUpload;

    public static FileIncidentReportDialogFragment newInstance() {
        final FileIncidentReportDialogFragment fragment = new FileIncidentReportDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
        Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_file_incident_report, null);
        ButterKnife.bind(this, view);
        initIncidentTypeList();
        initRecyclerView();
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

    private void initIncidentTypeList() {
        final ArrayList<String> items = new ArrayList<>();
        items.add("Traffic road report");
        items.add("Accident report");
        items.add("Waste disposal report");
        items.add("Crime/Illegal drug report");
        items.add("Flood report");
        items.add("Environmental report");
        items.add("Disaster report");
        initSpinner(spnr_incident_type, items);
    }

    private void initSpinner(final Spinner spinner, final ArrayList<String> items) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spinner, items);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.ll_add_photo)
    public void addPhoto() {
        if (filesToUpload.size() < 3) {
//            new BottomSheet.Builder(getActivity())
//                    .title("Upload Photo").sheet(R.menu.menu_upload_image)
//                    .listener((dialog, which) -> {
//                        switch (which) {
//                            case R.id.open_gallery:
//                                final Intent intent = new Intent();
//                                intent.setType("image/*");
//                                intent.setAction(Intent.ACTION_GET_CONTENT);//
//                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
//                                break;
//                            case R.id.open_camera:
//                                final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                try {
//                                    fileToUpload = activity.createImageFile();
//                                    fileUri = Uri.fromFile(fileToUpload);
//                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                                    startActivityForResult(cameraIntent, CAPTURE_IMAGE);
//                                } catch (Exception ex) {
//                                    activity.showConfirmDialog("","Capture Image",
//                                            "We can't get your image. Please try again.","Close","",null);
//                                }
//                                break;
//                        }
//                    }).show();
        } else {
            activity.showConfirmDialog("","Upload Photo","Sorry, but you have already reached the maximum " +
                    " no of photos for upload.","Close","",null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                final String fileName = "incident_image_"+ activity.getSDF().format(Calendar.getInstance().getTime());
                filesToUpload.add(activity.getFile(data.getData(),fileName+".jpg"));
            } else {
                LogHelper.log("s3","captured image absolute file --> " + fileToUpload.getAbsolutePath());
                filesToUpload.add(activity.rotateBitmap(fileUri.getPath()));
            }
            rv_images.getAdapter().notifyDataSetChanged();
        }
    }

    private void initRecyclerView() {
        final FileToUploadAdapter adapter = new FileToUploadAdapter(getActivity(),filesToUpload);
        adapter.setOnSelectImageListener(position -> {
            final File file = filesToUpload.get(position);
            file.delete();
            filesToUpload.remove(position);
            rv_images.getAdapter().notifyDataSetChanged();
        });
        rv_images.setAdapter(adapter);
    }

    @OnClick(R.id.btn_file_report)
    public void fileIncidentReport() {
        final String incidentDescription = et_incident_description.getText().toString().trim();
        final String incidentLocation = et_incident_location.getText().toString().trim();

        if (incidentDescription.isEmpty()) {
            activity.setError(et_incident_description, AppConstants.WARN_FIELD_REQUIRED);
        } else if (incidentLocation.isEmpty()) {
            activity.setError(et_incident_location, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onFileIncidentReportListener != null) {
                onFileIncidentReportListener.onFileReport(incidentDescription,incidentLocation,
                        spnr_incident_type.getSelectedItem().toString(),filesToUpload);
            }
        }
    }

    @OnClick(R.id.btn_cancel_report)
    public void cancelReport() {
        if (filesToUpload.size() > 0 || !et_incident_description.getText().toString().trim().isEmpty()
                || !et_incident_location.getText().toString().trim().isEmpty()) {
            activity.showConfirmDialog("", "Incident Report", "Are you sure you want to discard your " +
                    " report?", "Yes", "No", new OnConfirmDialogListener() {
                @Override
                public void onConfirmed(String action) {
                    if (onFileIncidentReportListener != null) {
                        onFileIncidentReportListener.onCancelReport(filesToUpload);
                    }
                }

                @Override
                public void onCancelled(String action) {

                }
            });
        } else {
            if (onFileIncidentReportListener != null) {
                onFileIncidentReportListener.onCancelReport(filesToUpload);
            }
        }
    }

    public interface OnFileIncidentReportListener {
        void onFileReport(final String incidentDescription, final String incidentLocation,
                          final String incidentType, final ArrayList<File> filesToUpload);
        void onCancelReport(final ArrayList<File> filesToUpload);
    }

    public void setOnFileIncidentReportListener(OnFileIncidentReportListener onFileIncidentReportListener) {
        this.onFileIncidentReportListener = onFileIncidentReportListener;
    }
}
