/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.supplier;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class SupplierId extends EMCString {

    /** Creates a new instance of SupplierId */
    public SupplierId() {
        this.setEmcLabel("Supp-Code");
        this.setMandatory(true);
        this.setEditable(true);
    }
}
