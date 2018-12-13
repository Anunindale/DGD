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
public class SupplierBank extends EMCString{

    public SupplierBank() {
        this.setEmcLabel("Suppllier Bank");
        this.setStringSize(50);
        this.setLowerCaseAllowed(true);
        this.setMandatory(false);
    }

}
