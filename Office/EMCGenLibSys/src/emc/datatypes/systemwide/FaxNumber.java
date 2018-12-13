package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class FaxNumber extends EMCString {

    /** Creates a new instance of  EMCString*/
    public FaxNumber() {
        this.setEmcLabel("Fax Number");
        this.setEditable(true);
        this.setStringSize(20);
    }
}
