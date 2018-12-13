/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.creditnoteinvoice.foreignkeys;

import emc.datatypes.creditors.creditnoteinvoice.CreditNoteInvoiceNumber;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class CreditNoteInvoiceNumberFK extends CreditNoteInvoiceNumber {

    public CreditNoteInvoiceNumberFK() {
        this.setRelatedTable(CreditorsCreditNoteInvoiceMaster.class.getName());
        this.setRelatedField("creditNoteInvoiceNumber");
        this.setNumberSeqAllowed(false);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}

