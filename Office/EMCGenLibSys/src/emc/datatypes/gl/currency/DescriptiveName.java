package emc.datatypes.gl.currency;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DescriptiveName extends EMCString {

    /** Creates a new instance of DescriptiveName */
    public DescriptiveName() {
        this.setEmcLabel("Descriptive Name");
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(100);
    }
}
