/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.leavetype.HRLeaveTypeForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRLeaveTypeMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HRLeaveTypeMenu() {
        this.setClassPath(HRLeaveTypeForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Leave Type");
        this.setToolTipText("Setup and edit leave types");
    }
}
