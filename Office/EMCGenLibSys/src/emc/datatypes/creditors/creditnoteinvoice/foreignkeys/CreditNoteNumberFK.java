/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.creditnoteinvoice.foreignkeys;

import emc.datatypes.creditors.creditnoteinvoice.CreditNoteNumber;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;

/**
 *
 * @author wikus
 */
public class CreditNoteNumberFK extends CreditNoteNumber {

    public CreditNoteNumberFK() {
        this.setRelatedTable(CreditorsCreditNoteInvoiceMaster.class.getName());
        this.setRelatedField("creditNoteInvoiceNumber");
        this.setNumberSeqAllowed(false);
    }
}

