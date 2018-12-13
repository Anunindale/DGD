/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.transactions.resources.TotalCreditHeldForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsTotalCreditHeldMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsTotalCreditHeldMI. */
    public DebtorsTotalCreditHeldMI() {
        this.setClassPath(TotalCreditHeldForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Total Credit Held");
        this.setToolTipText("View total credit held for a particular customer.");
        this.setDoNotOpenForm(true);
    }
}
