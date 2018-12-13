/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.suppliergroups;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class SupplierGroupId extends EMCString {

    /** Creates a new instance of SupplierGroupId */
    public SupplierGroupId() {
        this.setEmcLabel("Supplier Group");
        this.setColumnWidth(15);
        this.setMandatory(true);
        this.setEditable(true);
    }
}
