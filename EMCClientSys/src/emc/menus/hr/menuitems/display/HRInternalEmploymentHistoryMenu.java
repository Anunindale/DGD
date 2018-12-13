/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.internalemploymenthistory.HRInternalEmploymentHistoryForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRInternalEmploymentHistoryMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HRInternalEmploymentHistoryMenu() {
        this.setClassPath(HRInternalEmploymentHistoryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Internal Employment History");
        this.setToolTipText("Setup and edit internal employment history");
    }
}
