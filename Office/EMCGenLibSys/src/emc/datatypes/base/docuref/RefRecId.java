package emc.datatypes.base.docuref;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RefRecId extends EMCString {

    /** Creates a new instance of RefRecId */
    public RefRecId() {
        this.setEmcLabel("Ref Record Id");
        this.setMandatory(true);
        this.setStringSize(50);
    }
}
