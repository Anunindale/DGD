package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.pivotalinst.HRPivotalInstForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author claudette
 */
public class HRPivotalInstMI extends EMCMenuItem {

    /** Creates a new instance of HRPivotalInstMI. */
    public HRPivotalInstMI() {
        this.setClassPath(HRPivotalInstForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Pivotal Inst");
        this.setToolTipText("View and Edit Pivotal Inst data");
    }
}