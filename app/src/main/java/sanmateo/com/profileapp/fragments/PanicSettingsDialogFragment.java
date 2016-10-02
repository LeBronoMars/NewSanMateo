package sanmateo.com.profileapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.PanicContactsAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.helpers.RealmHelper;
import sanmateo.com.profileapp.interfaces.OnConfirmDialogListener;
import sanmateo.com.profileapp.models.realm.PanicContact;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class PanicSettingsDialogFragment extends DialogFragment {

    private View view;
    private BaseActivity activity;
    private OnPanicContactListener onPanicContactListener;
    @BindView(R.id.lv_contacts) ListView lv_contacts;
    @BindView(R.id.tv_no_contact) TextView tv_no_contact;
    private static final int READ_PHONE_BOOK = 1;
    private RealmList<PanicContact> contacts = new RealmList<>();
    private RealmHelper<PanicContact> realmHelper = new RealmHelper<>();

    public static PanicSettingsDialogFragment newInstance() {
        final PanicSettingsDialogFragment frag = new PanicSettingsDialogFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_panic_settings,null);
        activity = (BaseActivity) getActivity();
        contacts.clear();
        contacts.addAll(realmHelper.findAll(PanicContact.class));
        ButterKnife.bind(this, view);
        final PanicContactsAdapter adapter = new PanicContactsAdapter(getActivity(), contacts);
        adapter.setOnDeleteContactListener(contact -> activity.showConfirmDialog("",
                "Delete Contact", "Are you sure you want to remove "
                + contact.getContactName() + " from your panic contact list?", "Yes", "No",
                new OnConfirmDialogListener() {
            @Override
            public void onConfirmed(String action) {
                realmHelper.deleteContact(contact.getContactNo());
                refreshList();
                activity.showToast("Contact successfully deleted!");
            }

            @Override
            public void onCancelled(String action) {}
        }));
        lv_contacts.setAdapter(adapter);
        checkContactSize();
        final Dialog mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    public void refreshList() {
        contacts.clear();
        contacts.addAll(realmHelper.findAll(PanicContact.class));
        ((BaseAdapter)lv_contacts.getAdapter()).notifyDataSetChanged();
        checkContactSize();
    }

    private void checkContactSize() {
        if (contacts.size() == 0) {
            tv_no_contact.setVisibility(View.VISIBLE);
            lv_contacts.setVisibility(View.GONE);
        } else {
            tv_no_contact.setVisibility(View.GONE);
            lv_contacts.setVisibility(View.VISIBLE);
        }
        PrefsHelper.setInt(activity, "panicContactSize",contacts.size());
    }

    @OnClick(R.id.btn_select)
    public void openContacts() {
        final Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, READ_PHONE_BOOK);
    }

    @OnClick(R.id.btn_close)
    public void closeWindow() {
        onPanicContactListener.onDismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onPanicContactListener.onDismiss();
    }

    public interface OnPanicContactListener {
        void onDismiss();
    }

    public void setOnPanicContactListener(OnPanicContactListener onPanicContactListener) {
        this.onPanicContactListener = onPanicContactListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_PHONE_BOOK) {
            if (resultCode == Activity.RESULT_OK) {
                final Uri contact = data.getData();
                final ContentResolver cr = getActivity().getContentResolver();
                final Cursor c = getActivity().getContentResolver().query(contact, null, null, null, null);
                while (c.moveToNext()) {
                    final String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    final String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        final Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
                        while (pCur.moveToNext()) {
                           final  String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (realmHelper.isExisting(phone)) {
                                activity.showToast("Contact already in your list");
                            } else {
                                realmHelper.createRecord(new PanicContact(name, phone));
                                activity.showToast("Contact successfully added!");
                                refreshList();
                            }
                        }
                    } else {
                        activity.showToast("Empty phone number!");
                    }
                }
            }
        }
    }
}
