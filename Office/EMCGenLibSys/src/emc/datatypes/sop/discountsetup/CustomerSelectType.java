/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.discountsetup;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class CustomerSelectType extends EMCString {

    /** Creates a new instance of CustomerSelectType. */
    public CustomerSelectType() {
        this.setEmcLabel("Cust. Sel. Type");
        this.setMandatory(true);
    }
}
