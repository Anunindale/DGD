/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.creditors.creditnoteinvoice;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CreditNoteInvoiceStatus extends EMCString {

    public CreditNoteInvoiceStatus() {
        this.setEmcLabel("Status");
        this.setMandatory(true);
    }

}
