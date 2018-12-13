package emc.datatypes.base.reporttools.reporttext;

import emc.datatypes.EMCString;

/** 
*
* @author claudette
*/
public class Part extends EMCString {

    /** Creates a new instance of Part. */
    public Part() {
        this.setEmcLabel("Part");
        this.setMandatory(true);
    }
}