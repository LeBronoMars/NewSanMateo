package sanmateo.com.profileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.EmergencyKitAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.helpers.AppConstants;
import sanmateo.com.profileapp.helpers.PrefsHelper;
import sanmateo.com.profileapp.models.others.EmergencyKits;


public class EmergencyKitActivity extends BaseActivity {

    @BindView(R.id.lv_emergency_kits) ListView lv_emergency_kits;
    private EmergencyKitAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_kit);
        ButterKnife.bind(this);
        setToolbarTitle("Emergency Kit");
        gson = new Gson();
        initList();
    }

    private void initList() {
        adapter = new EmergencyKitAdapter(this, getEmergencyKits());
        adapter.setOnCheckBoxPressed(emergencyKits -> saveEmergencyKits(emergencyKits));
        lv_emergency_kits.setAdapter(adapter);
    }

    private ArrayList<EmergencyKits> getEmergencyKits() {
        ArrayList<EmergencyKits> kits = new ArrayList<>();
        if (PrefsHelper.getString(this, AppConstants.PREFS_LOCAL_EMERGENCY_KITS).isEmpty()) {
            kits.add(new EmergencyKits("Water, one gallon of water per person per day for atleast" +
                    " three days, for drinking and sanitation", false));
            kits.add(new EmergencyKits("Food, atleast three day supply of non-perishable food", false));
            kits.add(new EmergencyKits("Battery-powered or hand crank radio and a NOAA Weather Radio" +
                    " with tone alert and extra batteries for both", false));
            kits.add(new EmergencyKits("Flashlight and extra batteries", false));
            kits.add(new EmergencyKits("First aid kit", false));
            kits.add(new EmergencyKits("Whistle to signal for help", false));
            kits.add(new EmergencyKits("Dust mask, to help filter contaminated air and plastic" +
                    " sheeting and duct tape to shelter-in-place", false));
            kits.add(new EmergencyKits("Moist towelettes, garbage bags and plastic ties for personal" +
                    " sanitation", false));
            kits.add(new EmergencyKits("Wrench or pliers to turn utilities", false));
            kits.add(new EmergencyKits("Can opener for food (if kit contains canned food)", false));
            kits.add(new EmergencyKits("Local maps", false));
            kits.add(new EmergencyKits("Cell phone with chargers, inverter or solar charger", false));
        } else {
            kits.addAll(toKits(PrefsHelper.getString(this,
                    AppConstants.PREFS_LOCAL_EMERGENCY_KITS)));
        }
        return kits;
    }

    private void saveEmergencyKits(ArrayList<EmergencyKits> emergencyKits) {
        PrefsHelper.setString(this, AppConstants.PREFS_LOCAL_EMERGENCY_KITS, toJson(emergencyKits));
    }

    private String toJson(ArrayList<EmergencyKits> emergencyKits) {
        String json = gson.toJson(emergencyKits);
        return json;
    }

    private ArrayList<EmergencyKits> toKits(String jsonString) {
        ArrayList<EmergencyKits> listKits = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String label = object.getString("label");
                boolean checkedState = object.getBoolean("checkedState");
                listKits.add(new EmergencyKits(label, checkedState));
            }
        } catch (JSONException ex) {
        }
        return listKits;
    }

    @OnClick(R.id.tvLink)
    public void goToLink() {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.fema.gov")));
    }
}
