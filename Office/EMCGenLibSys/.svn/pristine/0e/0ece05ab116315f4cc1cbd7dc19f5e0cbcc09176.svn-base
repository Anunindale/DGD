/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.transactions.datasource.SerialNo;
import emc.entity.inventory.register.InventoryStocktakeRegister;

/**
 *
 * @author wikus
 */
public class SerialDS extends SerialNo {

    public SerialDS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedTable(InventoryStocktakeRegister.class.getName());
        dsr.setRelatedField("serial");
        dsr.setPKField("serial");
        dsr.setFKField("serial");
    	this.setColumnWidth(37);
        this.setDsRelation(dsr);
    }
}
