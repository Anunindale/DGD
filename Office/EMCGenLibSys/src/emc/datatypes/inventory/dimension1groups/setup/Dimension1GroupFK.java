/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension1groups.setup;

import emc.datatypes.inventory.dimension1groups.Dimension1GroupId;
import emc.entity.inventory.dimensions.InventoryDimension1Group;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author rico
 */
public class Dimension1GroupFK extends Dimension1GroupId {
    public Dimension1GroupFK(){
        this.setRelatedTable(InventoryDimension1Group.class.getName());
        this.setRelatedField("dimensionGroupId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}
