package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PhysicalAddress3 extends EMCString {

    /** Creates a new instance of PhysicalAddress3 */
    public PhysicalAddress3() {
        this.setEmcLabel("Address Line 3");
        this.setEditable(true);
                this.setLowerCaseAllowed(true);

        this.setStringSize(50);
    }
}
