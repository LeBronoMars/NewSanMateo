package sanmateo.com.profileapp.models.others;

/**
 * Created by ctmanalo on 8/23/16.
 */
public class EmergencyKits {

    private String label;
    private boolean checkedState;

    public EmergencyKits(String label, boolean checkedState) {
        this.label = label;
        this.checkedState = checkedState;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCheckedState() {
        return checkedState;
    }

    public void setCheckedState(boolean checkedState) {
        this.checkedState = checkedState;
    }
}
