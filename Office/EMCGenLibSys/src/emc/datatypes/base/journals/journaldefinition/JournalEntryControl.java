package emc.datatypes.base.journals.journaldefinition;

import emc.datatypes.EMCBoolean;

/**
 * 
 * @author wikus
 */
public class JournalEntryControl extends EMCBoolean {

    /** Creates a new instance of JournalEntryControl */
    public JournalEntryControl() {
        this.setEmcLabel("Entry Control");
        //this.setColumnWidth(75);
        this.setEditable(true);
    }
}
