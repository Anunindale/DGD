package emc.datatypes.base.systemlog;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Type extends EMCString {

    /** Creates a new instance of Type */
    public Type() {
        this.setEmcLabel("Type");
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(100);
    }
}
