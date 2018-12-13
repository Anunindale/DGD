/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.journalmaster.foreignkey;

import emc.datatypes.gl.journalmaster.JournalNumber;
import emc.entity.gl.journals.GLJournalMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class JournalNumberFK extends JournalNumber {

    public JournalNumberFK() {
        this.setRelatedTable(GLJournalMaster.class.getName());
        this.setRelatedField("journalNumber");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
