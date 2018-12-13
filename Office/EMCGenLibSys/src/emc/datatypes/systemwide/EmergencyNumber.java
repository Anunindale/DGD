package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class EmergencyNumber extends EMCString {

    /** Creates a new instance of  EMCString*/
    public EmergencyNumber() {
        this.setEmcLabel("Emergency Number");
        this.setEditable(true);
    	this.setColumnWidth(71);
        this.setStringSize(100);
    }
}
