package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PhysicalAddress4 extends EMCString {

    /** Creates a new instance of PhysicalAddress4 */
    public PhysicalAddress4() {
        this.setEmcLabel("Address Line 4");
        this.setEditable(true);
                this.setLowerCaseAllowed(true);

        this.setStringSize(50);
    }
}
