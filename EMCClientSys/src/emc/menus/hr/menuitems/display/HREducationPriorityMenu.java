/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.educationpriority.HREducationPriorityForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HREducationPriorityMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HREducationPriorityMenu() {
        this.setClassPath(HREducationPriorityForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Training Priority");
        this.setToolTipText("Setup and edit Training Priority");
    }
}
