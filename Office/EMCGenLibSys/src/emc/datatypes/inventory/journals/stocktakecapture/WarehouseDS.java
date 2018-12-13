/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.entity.inventory.journals.InventoryJournalLines;

/**
 *
 * @author wikus
 */
public class WarehouseDS extends WarehouseFKNotMandatory {

    public WarehouseDS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryJournalLines.class.getName());
        dsr.setRelatedTable(InventoryJournalLines.class.getName());
        dsr.setRelatedField("warehouse");
        dsr.setPKField("warehouse");
        dsr.setFKField("warehouse");
    	this.setColumnWidth(48);
        this.setDsRelation(dsr);
    }
}
