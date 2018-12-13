package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.pivotalstudyfield.HRPivotalStudyFieldForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author claudette
 */
public class HRPivotalStudyFieldMI extends EMCMenuItem {

    /** Creates a new instance of HRPivotalStudyFieldMI. */
    public HRPivotalStudyFieldMI() {
        this.setClassPath(HRPivotalStudyFieldForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Pivotal Study Field");
        this.setToolTipText("View and Edit Pivotal Study Field data");
    }
}