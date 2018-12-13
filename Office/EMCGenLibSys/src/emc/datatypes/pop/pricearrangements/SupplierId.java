package emc.datatypes.pop.pricearrangements;

import emc.datatypes.pop.supplier.foreignkeys.SupplierIdFKNotMandatory;

/** 
*
* @author riaan
*/
public class SupplierId extends SupplierIdFKNotMandatory {

    /** Creates a new instance of SupplierId. */
    public SupplierId() {
        this.setEmcLabel("Supplier");
    }
}