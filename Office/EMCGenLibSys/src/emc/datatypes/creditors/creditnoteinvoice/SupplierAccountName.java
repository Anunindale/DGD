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
public class SupplierAccountName extends EMCString {

    public SupplierAccountName() {
        this.setEmcLabel("Suppllier Account Name");
        this.setStringSize(50);
        this.setLowerCaseAllowed(false);
    }
}
