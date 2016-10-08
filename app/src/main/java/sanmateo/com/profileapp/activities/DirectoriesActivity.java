package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.DirectoriesAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.AppConstants;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class DirectoriesActivity extends BaseActivity {

    @BindView(R.id.elv_directories) ExpandableListView lvDirectories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directories);
        ButterKnife.bind(this);
        setToolbarTitle("Directories");
        final DirectoriesAdapter adapter = new DirectoriesAdapter(this, getDepartments(),
                getContactNos(), getEmail());
        adapter.setOnDirectoryActionListener(new DirectoriesAdapter.OnDirectoryActionListener() {
            @Override
            public void onCallDirectory(String contactNo) {
                if (contactNo.equals("N/A")) {
                    showToast(AppConstants.WARN_INVALID_CONTACT_NO);
                } else {
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contactNo));
                    startActivity(intent);
                }
            }

            @Override
            public void onSendEmailToDirectory(String email) {
                final Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                        email, null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
        lvDirectories.setAdapter(adapter);
    }

    private ArrayList<String> getDepartments () {
        ArrayList<String> header = new ArrayList<>();
        header.add("Business Permit and Licensing Office");
        header.add("Human Resource Department");
        header.add("ICCO");
        header.add("ICTO");
        header.add("Local Civil Registrars Office");
        header.add("Mayor's Office");
        header.add("Motorpool");
        header.add("MTFRO");
        header.add("Municipal Accounting Office");
        header.add("Municipal Administrators Office");
        header.add("Municipal Agriculture Office");
        header.add("Municipal Assessors Office");
        header.add("Municipal Budget Office");
        header.add("Municipal DILG");
        header.add("Municipal Engineering Office");
        header.add("Municipal Environment Office");
        header.add("Municipal Health Office");
        header.add("Municipal Planning and Development Office");
        header.add("Municipal Risk Reduction Management Office");
        header.add("Municipal Social Welfare");
        header.add("Municipal Tourism Office");
        header.add("Municipal Treasurers Office");
        header.add("OPSS");
        header.add("PAESO");
        header.add("PESO");
        header.add("Procurement Office");
        header.add("Sangguniang Bayan");
        header.add("Sanitary Office");
        header.add("Solid Waste");
        header.add("Traffic Management Office");
        header.add("Vice Mayor's Office");
        return header;
    }

    private ArrayList<String> getContactNos () {
        ArrayList<String> data = new ArrayList<>();
        data.add("706-7924");
        data.add("N/A");
        data.add("706-7921");
        data.add("212-6498");
        data.add("570-2080");
        data.add("706-7920");
        data.add("N/A");
        data.add("N/A");
        data.add("997-3840");
        data.add("N/A");
        data.add("696-9241");
        data.add("570-2079");
        data.add("570-6943");
        data.add("570-6952");
        data.add("570-2070");
        data.add("N/A");
        data.add("N/A");
        data.add("N/A");
        data.add("570-6846");
        data.add("703-4494");
        data.add("N/A");
        data.add("941-7728");
        data.add("706-7921");
        data.add("N/A");
        data.add("N/A");
        data.add("706-7922");
        data.add("570-2069");
        data.add("N/A");
        data.add("N/A");
        data.add("706-7921");
        data.add("997-6658");
        return data;
    }

    private ArrayList<String> getEmail () {
        ArrayList<String> email = new ArrayList<>();
        email.add("bplo@sanmateo.gov.ph");
        email.add("hroffice@sanmateo.gov.ph");
        email.add("iccoffice@sanmateo.gov.ph");
        email.add("ICTO@sanmateo.gov.ph");
        email.add("civilregistrarsoffice@sanmateo.gov.ph");
        email.add("mayorsoffice@sanmateo.gov.ph");
        email.add("motorpooloffice@sanmateo.gov.ph");
        email.add("mtfro@sanmateo.gov.ph");
        email.add("accountingoffice@sanmateo.gov.ph");
        email.add("adminoffice@sanmateo.gov.ph");
        email.add("agricultureoffice@sanmateo.gov.ph");
        email.add("assessorsoffice@sanmateo.gov.ph");
        email.add("budgetoffice@sanmateo.gov.ph");
        email.add("dilg@sanmateo.gov.ph");
        email.add("engineeringoffice@sanmateo.gov.ph");
        email.add("environmentoffice@sanmateo.gov.ph");
        email.add("healthoffice@sanmateo.gov.ph");
        email.add("mpdo@sanmateo.gov.ph");
        email.add("mdrrmoffice@sanmateo.gov.ph");
        email.add("mswdo@sanmateo.gov.ph");
        email.add("tourismoffice@sanmateo.gov.ph");
        email.add("treasuryoffice@sanmateo.gov.ph");
        email.add("opss@sanmateo.gov.ph");
        email.add("paiso@sanmateo.gov.ph");
        email.add("pesoffice@sanmateo.gov.ph");
        email.add("procurementoffice@sanmateo.gov.ph");
        email.add("sboffice@sanmateo.gov.ph");
        email.add("sanitaryoffice@sanmateo.gov.ph");
        email.add("solidwasteoffice@sanmateo.gov.ph");
        email.add("tmoffice@sanmateo.gov.ph");
        email.add("vicemayorsoffice@sanmateo.gov.ph");
        return email;
    }
}
