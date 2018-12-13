/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.invoicemastersuper.foreignkeys;

import emc.datatypes.debtors.invoicemastersuper.InvCNNumber;
import emc.entity.debtors.superclasses.DebtorsInvoiceMasterSuper;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class InvCNNumberFK extends InvCNNumber {

    /** Creates a new instance of InvCNNumberFK. */
    public InvCNNumberFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedField("invCNNumber");
        this.setRelatedTable(DebtorsInvoiceMasterSuper.class.getName());
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
