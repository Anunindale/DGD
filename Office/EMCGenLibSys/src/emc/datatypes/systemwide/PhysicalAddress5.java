package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PhysicalAddress5 extends EMCString {

    /** Creates a new instance of PhysicalAddress5 */
    public PhysicalAddress5() {
        this.setEmcLabel("Address Line 5");
        this.setEditable(true);
                this.setLowerCaseAllowed(true);

        this.setStringSize(50);
    }
}
