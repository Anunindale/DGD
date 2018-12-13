package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PhysicalAddress1 extends EMCString {

    /** Creates a new instance of PhysicalAddress1 */
    public PhysicalAddress1() {
        this.setEmcLabel("Physical Address Line 1");
        this.setEditable(true);
        this.setStringSize(50);
        this.setLowerCaseAllowed(true);
        this.setMandatory(true);
    }
}
