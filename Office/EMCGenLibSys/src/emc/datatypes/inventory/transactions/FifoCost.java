package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCDouble;

/**
 * 
 * @author wikus
 */
public class FifoCost extends EMCDouble {

    /** Creates a new instance of Cost */
    public FifoCost() {
        this.setEmcLabel("FIFO Value");
        this.setMandatory(true);
        this.setEditable(true);
        this.setNumberDecimalsDisplay(2);
        this.setMaxValue(1000000000d);
        this.setMinValue(-1000000000d);
    }
}
