package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCDate;

/**
 * 
 * @author wikus
 */
public class PhysicalDate extends EMCDate {

    /** Creates a new instance of PhysicalDate */
    public PhysicalDate() {
        this.setEmcLabel("Physical");
        this.setMandatory(false);
    	this.setColumnWidth(66);
      
    }
}
