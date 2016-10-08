package sanmateo.com.profileapp.customviews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 7/14/16.
 */
public class ContactView extends LinearLayout {

    private TextView tvContact;

    public ContactView(Context context) {
        super(context);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactView(final Context context, String no) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_no_layout, null);
        tvContact = (TextView) view.findViewById(R.id.tvContact);
        tvContact.setText(no);
        tvContact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvContact.getText().toString()));
                context.startActivity(intent);
            }
        });
        addView(view);
    }
}
