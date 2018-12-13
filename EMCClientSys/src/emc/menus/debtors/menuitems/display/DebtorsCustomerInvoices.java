/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.customerinvoice.CustomerInvoiceForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsCustomerInvoices extends EMCMenuItem {

    /** Creates a new instance of DebtorsCustomerInvoices */
    public DebtorsCustomerInvoices() {
        this.setClassPath(CustomerInvoiceForm.class.getName());
        this.setMenuItemType(enumMenuItems.DISPLAY);
        this.setMenuItemName("Customer Invoice");
        this.setToolTipText("Edit Customer Invoices");
    }
}
