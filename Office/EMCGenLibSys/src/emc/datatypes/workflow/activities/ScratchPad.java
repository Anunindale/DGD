package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ScratchPad extends EMCString {

    /** Creates a new instance of ScratchPad */
    public ScratchPad() {
        this.setEmcLabel("Scratch Notes");
        this.setEditable(true);
        this.setStringSize(2000);
        this.setLowerCaseAllowed(true);
    }
}
