package emc.datatypes.base.journals.journaldefinition;

import emc.datatypes.EMCBoolean;

/**
 * 
 * @author wikus
 */
public class JournalApprovalRequired extends EMCBoolean {

    /** Creates a new instance of JournalApprovalRequired */
    public JournalApprovalRequired() {
        this.setEmcLabel("Approval Required");
        //this.setColumnWidth(80);
        this.setEditable(true);
    }
}
