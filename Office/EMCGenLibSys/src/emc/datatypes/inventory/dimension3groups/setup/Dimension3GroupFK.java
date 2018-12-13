/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension3groups.setup;

import emc.datatypes.inventory.dimension3groups.Dimension3GroupId;
import emc.entity.inventory.dimensions.InventoryDimension3Group;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author rico
 */
public class Dimension3GroupFK extends Dimension3GroupId {
    public Dimension3GroupFK(){
        this.setRelatedTable(InventoryDimension3Group.class.getName());
        this.setRelatedField("dimensionGroupId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}
