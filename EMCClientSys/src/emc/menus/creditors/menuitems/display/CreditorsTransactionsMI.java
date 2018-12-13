package emc.menus.creditors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.creditors.display.transactions.CreditorsTransactionsForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author Owner
 */
public class CreditorsTransactionsMI extends EMCMenuItem {

    /** Creates a new instance of CreditorsTransactionsMI. */
    public CreditorsTransactionsMI() {
        this.setClassPath(CreditorsTransactionsForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Transactions");
        this.setToolTipText("View and Edit Transactions data");
    }
}
