package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.salesgroup.DebtorsSalesGroupForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class DebtorsSalesGroupMenu extends EMCMenuItem {

    /** Creates a new instance of DebtorsCustomerGroupMenu */
    public DebtorsSalesGroupMenu() {
        this.setClassPath(DebtorsSalesGroupForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Sales Group");
        this.setToolTipText("Create and edit sales groups");
    }
}
