/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.pallet.foreignkeys;

import emc.datatypes.inventory.pallet.PalletPK;
import emc.entity.inventory.InventoryPallet;

/**
 *
 * @author wikus
 */
public class PalletIdFK extends PalletPK {

    public PalletIdFK() {
        this.setNumberSeqAllowed(false);
        //Do a find usages before changing this.
        this.setMandatory(false);
        this.setRelatedTable(InventoryPallet.class.getName());
    	this.setColumnWidth(43);
        this.setRelatedField("palletId");
    }
}
