/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.section.HRSectionForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRSectionMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HRSectionMenu() {
        this.setClassPath(HRSectionForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Section");
        this.setToolTipText("Setup and edit sections");
    }
}
