package emc.datatypes.base.numbersequences;

import emc.datatypes.EMCLong;

/**
 *
 * @author wikus
 */
public class NextAvailableNumber extends EMCLong {

    /** Creates a new instance of NextAvailableNumber */
    public NextAvailableNumber() {
        this.setEmcLabel("Next AvailableNumber");
        this.setEditable(true);
    }
}
