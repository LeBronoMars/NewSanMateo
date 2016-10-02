package sanmateo.com.profileapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmList;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.models.request.PanicContact;


/**
 * Created by rsbulanon on 10/2/16.
 */
public class PanicContactsAdapter extends BaseAdapter {

    private BaseActivity baseActivity;
    private Context context;
    private RealmList<PanicContact> contacts;
    private OnDeleteContactListener onDeleteContactListener;

    public PanicContactsAdapter(Context context, RealmList<PanicContact> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.baseActivity = (BaseActivity)context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public PanicContact getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PanicContact contact = getItem(i);
        view = baseActivity.getLayoutInflater().inflate(R.layout.row_panic_contacts, null, false);
        final TextView tvContactName = (TextView)view.findViewById(R.id.tv_contact_name);
        final TextView tvContactNo = (TextView)view.findViewById(R.id.tv_contact_no);
        final ImageView ivDelete = (ImageView)view.findViewById(R.id.iv_delete);

        tvContactName.setText(contact.getContactName());
        tvContactNo.setText(contact.getContactNo());
        ivDelete.setOnClickListener(view1 -> onDeleteContactListener.onDeleteContact(contact));
        return view;
    }

    public interface OnDeleteContactListener {
        void onDeleteContact(PanicContact contact);
    }

    public void setOnDeleteContactListener(OnDeleteContactListener onDeleteContactListener) {
        this.onDeleteContactListener = onDeleteContactListener;
    }
}
