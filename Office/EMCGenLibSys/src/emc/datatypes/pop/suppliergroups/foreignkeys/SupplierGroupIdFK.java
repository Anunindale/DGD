/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.suppliergroups.foreignkeys;

import emc.datatypes.pop.suppliergroups.SupplierGroupId;
import emc.entity.pop.POPSupplierGroup;

/**
 *
 * @author riaan
 */
public class SupplierGroupIdFK extends SupplierGroupId {

    /** Creates a new instance of SupplierGroupIdFK */
    public SupplierGroupIdFK() {
        this.setRelatedTable(POPSupplierGroup.class.getName());
        this.setRelatedField("supplierGroupId");
    }
}
