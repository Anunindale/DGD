package emc.datatypes.workflow.activities;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class Duration extends EMCDouble {

    /** Creates a new instance of Duration */
    public Duration() {
        this.setEmcLabel("Duration");
        this.setEditable(true);
        this.setNumberDecimalsDisplay(1);
    }
}
