package emc.datatypes.base.journals.journaldefinition;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class JournalDefinitionId extends EMCString {

    /** Creates a new instance of JournalDefinitionId */
    public JournalDefinitionId() {
        this.setEmcLabel("Definition Id");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(50);
    }
}
