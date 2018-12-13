/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.batchconsolidation.foreignkeys;

import emc.datatypes.inventory.batchconsolidation.ConsolidationNumber;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class ConsolidationNumberFK extends ConsolidationNumber {
    
    public ConsolidationNumberFK() {
        this.setRelatedTable(InventoryBatchConsolidationMaster.class.getName());
        this.setRelatedField("consolidationNumber");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
