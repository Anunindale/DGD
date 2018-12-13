package emc.menus.creditors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.creditors.display.creditorstransactionsettlementhistory.CreditorsTransactionSettlementHistoryForm;
import emc.framework.EMCMenuItem;

/** 
 *
 * @author Owner
 */
public class CreditorsTransactionSettlementHistoryMI extends EMCMenuItem {

    /** Creates a new instance of CreditorsTransactionSettlementHistoryMI. */
    public CreditorsTransactionSettlementHistoryMI() {
        this.setClassPath(CreditorsTransactionSettlementHistoryForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Allocation History");
        this.setToolTipText("View and Edit Transaction Settlement History data");
    }
}
