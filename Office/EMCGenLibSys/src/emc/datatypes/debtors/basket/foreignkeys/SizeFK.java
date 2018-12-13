/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket.foreignkeys;

import emc.datatypes.debtors.basket.Size;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class SizeFK extends Size {
    public SizeFK(){
        this.setRelatedTable(InventoryDimension2.class.getName());
        this.setRelatedField("dimensionId");
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
