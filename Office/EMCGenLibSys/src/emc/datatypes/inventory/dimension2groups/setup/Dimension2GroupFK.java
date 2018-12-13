/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension2groups.setup;

import emc.datatypes.inventory.dimension2groups.Dimension2GroupId;
import emc.entity.inventory.dimensions.InventoryDimension2Group;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author rico
 */
public class Dimension2GroupFK extends Dimension2GroupId {
    public Dimension2GroupFK(){
        this.setRelatedTable(InventoryDimension2Group.class.getName());
        this.setRelatedField("dimensionGroupId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }

}
