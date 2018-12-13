package emc.datatypes.base.journals.journaldefinition;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class JournalContraType extends EMCString {

    /** Creates a new instance of JournalContraType */
    public JournalContraType() {
        this.setEmcLabel("Contra Type");
        this.setColumnWidth(75);
        this.setEditable(true);
        this.setStringSize(50);
    }
}
