package emc.datatypes.base.unitsofmeasureconversion;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DimId extends EMCString {

    /** Creates a new instance of DimId */
    public DimId() {
        this.setEmcLabel("Dimention Id");
        this.setMandatory(false);
        this.setEditable(true);
        this.setStringSize(50);
    }
}
