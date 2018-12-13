package emc.datatypes.base.license;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class Validate extends EMCBoolean {

    /** Creates a new instance of Validate */
    public Validate() {
        this.setEmcLabel("Validation");
        this.setMandatory(true);
        this.setEditable(true);
    }
}
