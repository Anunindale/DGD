package emc.datatypes.base.postalcodes;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Suburb extends EMCString {

    /** Creates a new instance of Suburb */
    public Suburb() {
        this.setEmcLabel("Suburb");
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(100);
    }
}
