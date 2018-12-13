/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journalmaster.foreignkeys;

import emc.datatypes.inventory.journals.journalmaster.JournalNumber;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.datatypes.CallBeanLogicOptions;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class JournalNumberFK extends JournalNumber {

    /** Creates a new instance of JournalNumberFK. */
    public JournalNumberFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(InventoryJournalMaster.class.getName());
        this.setRelatedField("journalNumber");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setCallBeanOptions(CallBeanLogicOptions.DELETE_ONLY);
    }
}
