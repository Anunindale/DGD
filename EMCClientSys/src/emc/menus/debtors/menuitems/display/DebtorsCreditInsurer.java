/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.creditinsurer.CreditInsurerForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsCreditInsurer extends EMCMenuItem {

    /** Creates a new instance of DebtorsCreditInsurer */
    public DebtorsCreditInsurer() {
        this.setClassPath(CreditInsurerForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Credit Insurers");
        this.setToolTipText("Create and edit credit insurers.");
    }
}
