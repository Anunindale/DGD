package emc.datatypes.base.dblogsetup;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class LogField extends EMCString {

    /** Creates a new instance of LogField */
    public LogField() {
        this.setEmcLabel("Field");
        this.setStringSize(100);
    }
}
