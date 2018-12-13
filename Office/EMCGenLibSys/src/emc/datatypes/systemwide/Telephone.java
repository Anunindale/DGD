package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Telephone extends EMCString {

    /** Creates a new instance of Telephone */
    public Telephone() {
        this.setEmcLabel("Telephone");
        this.setEditable(true);
        this.setStringSize(20);
    }
}
