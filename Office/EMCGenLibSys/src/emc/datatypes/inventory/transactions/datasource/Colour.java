package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.Dimention3Interface;

/**
 * 
 * @author wikus
 */
public class Colour extends EMCString implements Dimention3Interface {

    /** Creates a new instance of Colour */
    public Colour() {
        this.setEmcLabel("Colour");
        this.setStringSize(20);
    }
}
