package emc.datatypes.base.docuref;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Type extends EMCString {

    /** Creates a new instance of Type */
    public Type() {
        this.setEmcLabel("Type");
        this.setEditable(true);
    	this.setColumnWidth(38);
        this.setStringSize(10);
    }
}
