/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.batchconsolidation;

import emc.datatypes.EMCLong;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class JournalLineRecordId extends EMCLong {
    
    public JournalLineRecordId() {
        this.setMandatory(true);
        this.setRelatedTable(InventoryJournalLines.class.getName());
        this.setRelatedField("recordID");
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}
