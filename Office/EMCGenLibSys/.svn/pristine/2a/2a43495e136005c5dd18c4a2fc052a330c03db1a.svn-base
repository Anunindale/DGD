/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.invoicemastersuper.datasource;

import emc.datatypes.debtors.invoicemastersuper.foreignkeys.*;
import emc.datatypes.debtors.invoicemastersuper.InvCNNumber;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class InvCNNumberNoNumSeq extends InvCNNumber {

    /** Creates a new instance of InvCNNumberFK. */
    public InvCNNumberNoNumSeq() {
        this.setNumberSeqAllowed(false);
        this.setRelatedField("invCNNumber");
        //Do not explicitely refer to a table.  Set table on subclasses for
        //Invoices and Credit Notes.
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
