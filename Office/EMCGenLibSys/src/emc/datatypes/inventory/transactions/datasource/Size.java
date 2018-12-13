package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.Dimention2Interface;

/**
 * 
 * @author wikus
 */
public class Size extends EMCString implements Dimention2Interface {

    /** Creates a new instance of Size */
    public Size() {
        this.setEmcLabel("Size");
        this.setStringSize(20);
    }
}
