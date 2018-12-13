/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.entity.inventory.InventoryWarehouse;

/**
 *
 * @author wikus
 */
public class WarehouseDS extends WarehouseFKNotMandatory {

    public WarehouseDS() {
        super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryWarehouse.class.getName());
        dsr.setRelatedField("warehouseId");
        dsr.setFKField("warehouse");
        dsr.setPKField("warehouseId");
        this.setDsRelation(dsr);
    }
}
