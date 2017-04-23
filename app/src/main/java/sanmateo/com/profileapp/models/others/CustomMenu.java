package sanmateo.com.profileapp.models.others;

/**
 * Created by avinnovz on 4/23/17.
 */

public class CustomMenu {

    private int menuIcon;
    private String menuTitle;
    private boolean isEditable;

    public CustomMenu(int menuIcon, String menuTitle, boolean isEditable) {
        this.menuIcon = menuIcon;
        this.menuTitle = menuTitle;
        this.isEditable = isEditable;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public boolean isEditable() {
        return isEditable;
    }
}
