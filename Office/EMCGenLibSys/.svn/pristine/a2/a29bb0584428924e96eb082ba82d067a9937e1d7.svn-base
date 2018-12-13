/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.transactions.datasource.Batch;
import emc.entity.inventory.register.InventoryStocktakeRegister;

/**
 *
 * @author wikus
 */
public class BatchDS extends Batch {

    public BatchDS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedField("batch");
        dsr.setPKField("batch");
        dsr.setFKField("batch");
    	this.setColumnWidth(70);
        this.setDsRelation(dsr);
    }
}
