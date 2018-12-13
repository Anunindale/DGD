package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PhysicalAddress2 extends EMCString {

    /** Creates a new instance of PhysicalAddress2 */
    public PhysicalAddress2() {
        this.setEmcLabel("Address Line 2");
        this.setEditable(true);
        this.setLowerCaseAllowed(true);
        this.setStringSize(50);
    }
}
