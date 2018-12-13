package emc.datatypes.base.dblog;

import emc.datatypes.base.country.*;
import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DBTable extends EMCString {

    /** Creates a new instance of Country */
    public DBTable() {
        this.setEmcLabel("Table");
        this.setColumnWidth(106);
        this.setMandatory(true);
        this.setEditable(true);
    }
}
