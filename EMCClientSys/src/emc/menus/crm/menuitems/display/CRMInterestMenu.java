/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.crm.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.crm.display.interest.CRMInterestForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class CRMInterestMenu extends EMCMenuItem {

    public CRMInterestMenu() {
        this.setClassPath(CRMInterestForm.class.getName());
        this.setMenuItemName("Interest");
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setToolTipText("Setup and manage interests");
    }
}
