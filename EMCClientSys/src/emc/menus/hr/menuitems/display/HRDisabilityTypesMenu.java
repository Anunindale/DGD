/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.disabilitytypes.HRDisabilityTypesForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRDisabilityTypesMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HRDisabilityTypesMenu() {
        this.setClassPath(HRDisabilityTypesForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Disability Types");
        this.setToolTipText("Setup and edit Disability Types");
    }
}
