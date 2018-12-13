package emc.datatypes.workflow.activityds;

import emc.datatypes.EMCBoolean;

/**
 * 
 * @author wikus
 */
public class DocumentAttached extends EMCBoolean {

    /** Creates a new instance of DocumentAttached */
    public DocumentAttached() {
        this.setEmcLabel("Document Attached");
        this.setEditable(true);
    }
}
