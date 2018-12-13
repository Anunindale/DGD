package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.pivotalprogram.HRPivotalProgramForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author claudette
 */
public class HRPivotalProgramMI extends EMCMenuItem {

    /** Creates a new instance of HRPivotalProgramMI. */
    public HRPivotalProgramMI() {
        this.setClassPath(HRPivotalProgramForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Pivotal Program");
        this.setToolTipText("View and Edit Pivotal Program data");
    }
}