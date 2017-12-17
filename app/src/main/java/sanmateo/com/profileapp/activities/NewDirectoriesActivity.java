package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.NewDirectoriesAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.models.others.Directory;


import static sanmateo.com.profileapp.adapters.NewDirectoriesAdapter.*;

/**
 * Created by rsbulanon on 17/12/2017.
 */

public class NewDirectoriesActivity extends BaseActivity {

    @BindView(R.id.rv_directories)
    RecyclerView rvDirectories;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_directories);

        unbinder = ButterKnife.bind(this);

        rvDirectories.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<Directory> directories = new ArrayList<>();

        directories.add(new Directory("Business Permit and Licensing Office", "bplo@sanmateo.gov.ph", "706-7924"));
        directories.add(new Directory("Human Resource Department", "hroffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("ICCO", "iccoffice@sanmateo.gov.ph", "706-7921"));
        directories.add(new Directory("ICTO", "ICTO@sanmateo.gov.ph", "212-6498"));
        directories.add(new Directory("Local Civil Registrars Office", "civilregistrarsoffice@sanmateo.gov.ph", "570-2080"));
        directories.add(new Directory("Mayor's Office", "mayorsoffice@sanmateo.gov.ph", "706-7920"));
        directories.add(new Directory("Motorpool", "motorpooloffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("MTFRO", "mtfro@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Municipal Accounting Office", "accountingoffice@sanmateo.gov.ph", "997-3840"));
        directories.add(new Directory("Municipal Administrators Office", "adminoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Municipal Agriculture Office", "agricultureoffice@sanmateo.gov.ph", "696-9241"));
        directories.add(new Directory("Municipal Assessors Office", "assessorsoffice@sanmateo.gov.ph", "570-2079"));
        directories.add(new Directory("Municipal Budget Office", "budgetoffice@sanmateo.gov.ph", "570-6943"));
        directories.add(new Directory("Municipal DILG", "dilg@sanmateo.gov.ph", "570-6952"));
        directories.add(new Directory("Municipal Engineering Office", "engineeringoffice@sanmateo.gov.ph", "570-2070"));
        directories.add(new Directory("Municipal Environment Office", "environmentoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Municipal Health Office", "healthoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Municipal Planning and Development Office", "mpdo@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Municipal Risk Reduction Management Office", "mdrrmoffice@sanmateo.gov.ph", "570-6846"));
        directories.add(new Directory("Municipal Social Welfare", "mswdo@sanmateo.gov.ph", "703-4494"));
        directories.add(new Directory("Municipal Tourism Office", "tourismoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Municipal Treasurers Office", "treasuryoffice@sanmateo.gov.ph", "941-7728"));
        directories.add(new Directory("OPSS", "opss@sanmateo.gov.ph", "706-7921"));
        directories.add(new Directory("PAESO","paiso@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("PESO", "pesoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Procurement Office", "procurementoffice@sanmateo.gov.ph", "706-7922"));
        directories.add(new Directory("Sangguniang Bayan", "sboffice@sanmateo.gov.ph", "570-2069"));
        directories.add(new Directory("Sanitary Office", "sanitaryoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Solid Waste", "solidwasteoffice@sanmateo.gov.ph", "N/A"));
        directories.add(new Directory("Traffic Management Office", "tmoffice@sanmateo.gov.ph", "706-7921"));
        directories.add(new Directory("Vice Mayor's Office", "vicemayorsoffice@sanmateo.gov.ph", "997-6658"));

        NewDirectoriesAdapter newDirectoriesAdapter = new NewDirectoriesAdapter(this, directories);

        newDirectoriesAdapter.setOnDirectoryActionListener(new OnDirectoryActionListener() {
            @Override
            public void call(String number) {
                if (number.equals("N/A")) {
                    showToast(AppConstants.WARN_INVALID_CONTACT_NO);
                } else {
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                }
            }

            @Override
            public void email(String email) {
                final Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                                                                                     email, null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
        rvDirectories.setAdapter(newDirectoriesAdapter);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
        animateToRight(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
