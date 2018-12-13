/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.supplier.foreignkeys;

import emc.datatypes.pop.supplier.SupplierId;
import emc.entity.pop.POPSuppliers;

/**
 *
 * @author riaan
 */
public class SupplierFK extends SupplierId {

    /** Creates a new instance of SupplierFK */
    public SupplierFK() {
        this.setColumnWidth(100);
        this.setRelatedTable(POPSuppliers.class.getName());
        this.setRelatedField("supplierId");
        this.setEditable(true);
    }
}
