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
public class CreditNoteInvoiceType extends EMCString{

    public CreditNoteInvoiceType() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
    }

}
