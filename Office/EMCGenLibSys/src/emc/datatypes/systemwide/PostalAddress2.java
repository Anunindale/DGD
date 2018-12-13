package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PostalAddress2 extends EMCString {

    /** Creates a new instance of PostalAddress2 */
    public PostalAddress2() {
        this.setEmcLabel("Postal Address Line 2");
        this.setEditable(true);
        this.setStringSize(50);
    }
}
