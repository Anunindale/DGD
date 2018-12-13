/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.debtors.output.DebtorsTransactionTotalsReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class DebtorsTransactionTotalsReportMenu extends EMCMenuItem {

    /** Creates a new instance of DebtorsCustomerAgingSummary */
    public DebtorsTransactionTotalsReportMenu() {
        this.setClassPath(DebtorsTransactionTotalsReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Transaction Audit Trail");
        this.setToolTipText("Print transaction totals per customer.");
    }
}
