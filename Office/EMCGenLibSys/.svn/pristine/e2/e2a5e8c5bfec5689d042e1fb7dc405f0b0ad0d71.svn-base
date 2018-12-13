/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket.foreignkeys;

import emc.datatypes.debtors.basket.Configuration;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class Dimension1FK extends Configuration {
    public Dimension1FK(){
        this.setRelatedTable(InventoryDimension1.class.getName());
        this.setRelatedField("dimensionId");
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
