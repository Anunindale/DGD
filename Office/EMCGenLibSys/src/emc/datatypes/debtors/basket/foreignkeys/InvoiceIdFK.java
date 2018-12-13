/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket.foreignkeys;

import emc.datatypes.debtors.basket.InvoiceId;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author stu
 */
public class InvoiceIdFK extends InvoiceId {
    public InvoiceIdFK(){
        this.setRelatedTable(DebtorsCustomerInvoiceMaster.class.getName());
        this.setRelatedField("invCNNumber");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
