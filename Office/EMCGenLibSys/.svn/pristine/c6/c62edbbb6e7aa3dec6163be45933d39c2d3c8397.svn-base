/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.costinggroups.foreignkeys;

import emc.datatypes.inventory.costinggroups.CostingGroupId;
import emc.entity.inventory.InventoryCostingGroup;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class CostingGroupIdFK extends CostingGroupId {

    /** Creates a new instance of CostingGroupIdFK */
    public CostingGroupIdFK() {
        this.setRelatedTable(InventoryCostingGroup.class.getName());
        this.setRelatedField("costingGroupId");
        this.setMandatory(true);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
    }
}
