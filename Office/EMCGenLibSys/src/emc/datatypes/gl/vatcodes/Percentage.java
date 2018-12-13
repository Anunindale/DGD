package emc.datatypes.gl.vatcodes;

import emc.datatypes.EMCDouble;


/**
 *
 * @author wikus
 */
public class Percentage extends EMCDouble {

    /** Creates a new instance of Percentage */
    public Percentage() {
        this.setEmcLabel("Percentage");
        this.setEditable(true);
        this.setNumberDecimalsDisplay(2);
    }
}
