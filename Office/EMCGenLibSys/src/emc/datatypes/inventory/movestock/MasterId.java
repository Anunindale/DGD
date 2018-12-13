/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.movestock;

import emc.datatypes.EMCString;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;

/**
 *
 * @author wikus
 */
public class MasterId extends EMCString {

    public MasterId() {
        this.setMandatory(true);
        this.setRelatedTable(InventoryMoveStockMaster.class.getName());
        this.setRelatedField("recordID");
    }
}
