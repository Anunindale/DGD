/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket.foreignkeys;

import emc.datatypes.debtors.basket.Colour;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class ColourFK extends Colour {
    public ColourFK(){
        this.setRelatedTable(InventoryDimension3.class.getName());
        this.setRelatedField("color");
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
