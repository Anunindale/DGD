package emc.datatypes.base.dblogsetup;

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
        this.setStringSize(100);
    }
}
