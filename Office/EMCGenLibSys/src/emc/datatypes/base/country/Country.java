package emc.datatypes.base.country;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Country extends EMCString {

    /** Creates a new instance of Country */
    public Country() {
        this.setEmcLabel("Country");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(100);
    }
}
