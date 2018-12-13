package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ExternalNotes extends EMCString {

    /** Creates a new instance of ExternalNotes */
    public ExternalNotes() {
        this.setEmcLabel("External Notes");
        this.setEditable(true);
        this.setStringSize(2000);
    }
}
