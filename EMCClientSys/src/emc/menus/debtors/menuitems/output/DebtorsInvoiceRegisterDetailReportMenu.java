/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.debtors.output.DebtorsInvoiceRegisterDetailReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class DebtorsInvoiceRegisterDetailReportMenu extends EMCMenuItem {

    public DebtorsInvoiceRegisterDetailReportMenu() {
        this.setClassPath(DebtorsInvoiceRegisterDetailReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Invoice Register Detail");
        this.setToolTipText("Print the invoice register detail.");
    }
}
