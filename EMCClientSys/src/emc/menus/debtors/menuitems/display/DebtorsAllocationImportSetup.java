/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.allocationimport.DebtorsAllocationImportForm;
import emc.forms.debtors.display.allocationimportsetup.DebtorsAllocationImportSetupForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsAllocationImportSetup extends EMCMenuItem {

    /** Creates a new instance of DebtorsAllocationImportSetup. */
    public DebtorsAllocationImportSetup() {
        this.setClassPath(DebtorsAllocationImportSetupForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Allocation Import Setup");
        this.setToolTipText("Set up allocation import parameters.");
    }
}
