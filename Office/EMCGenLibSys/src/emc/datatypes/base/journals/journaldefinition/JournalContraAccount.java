package emc.datatypes.base.journals.journaldefinition;

import emc.datatypes.gl.chartofaccounts.foreignkeys.AccountNumberFKNM;

/**
 * 
 * @author wikus
 */
public class JournalContraAccount extends AccountNumberFKNM {

    /** Creates a new instance of JournalContraAccount */
    public JournalContraAccount() {
        this.setEmcLabel("Contra Account");
        this.setColumnWidth(80);
        this.setEditable(true);
        this.setStringSize(50);
    }
}
