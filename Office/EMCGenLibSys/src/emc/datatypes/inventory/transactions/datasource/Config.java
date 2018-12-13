package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.Dimention1Interface;

/**
 * 
 * @author wikus
 */
public class Config extends EMCString implements Dimention1Interface {

    /** Creates a new instance of Config */
    public Config() {
        this.setEmcLabel("Config");
        this.setStringSize(20);
    }
}
