/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.hr.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.hr.display.institution.HRInstitutionForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class HRInstitutionMenu extends EMCMenuItem {

    /** Creates a new instance of  HRNationalityMenu*/
    public HRInstitutionMenu() {
        this.setClassPath(HRInstitutionForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Institution");
        this.setToolTipText("Setup and edit institutions");
    }
}
