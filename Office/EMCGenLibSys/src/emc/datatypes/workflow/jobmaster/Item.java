package emc.datatypes.workflow.jobmaster;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class Item extends EMCString {

    /** Creates a new instance of Item */
    public Item() {
        this.setEmcLabel("Item");
        this.setMandatory(false);
        this.setEditable(true);
        this.setStringSize(50);
    }
}
