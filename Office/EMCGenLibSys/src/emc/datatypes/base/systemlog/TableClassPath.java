package emc.datatypes.base.systemlog;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class TableClassPath extends EMCString {

    /** Creates a new instance of TableClassPath */
    public TableClassPath() {
        this.setEmcLabel("Table Class Path");
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(256);
    }
}
