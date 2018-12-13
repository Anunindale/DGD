/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.transactions.datasource.Pallet;
import emc.entity.inventory.register.InventoryStocktakeRegister;

/**
 *
 * @author wikus
 */
public class PalletDS extends Pallet {

    public PalletDS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedField("pallet");
        dsr.setPKField("pallet");
        dsr.setFKField("pallet");
    	this.setColumnWidth(63);
        this.setDsRelation(dsr);
    }
}
