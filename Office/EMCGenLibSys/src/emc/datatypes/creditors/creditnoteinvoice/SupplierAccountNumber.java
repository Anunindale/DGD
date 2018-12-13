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
public class SupplierAccountNumber extends EMCString {

    public SupplierAccountNumber() {
        this.setEmcLabel("Suppllier Account Number");
        this.setStringSize(50);
        this.setLowerCaseAllowed(true);
        this.setMandatory(false);
    }
}
