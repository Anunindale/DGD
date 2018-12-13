/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.scarcepriority.HRScarcePriorityForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRScarcePriorityMI  extends EMCMenuItem {

    public HRScarcePriorityMI() {
        this.setClassPath(HRScarcePriorityForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Scarce Priority");
        this.setToolTipText("View and Edit Scarce Priorities");
    }

}
