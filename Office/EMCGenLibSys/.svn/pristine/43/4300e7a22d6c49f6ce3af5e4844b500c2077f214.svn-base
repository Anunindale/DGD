/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.warehouses.foreignkeys;

import emc.datatypes.inventory.warehouses.Warehouse;
import emc.datatypes.systemwide.WarehouseInterface;
import emc.entity.inventory.InventoryWarehouse;

/**
 *
 * @author riaan
 */
public class WarehouseFKNotMandatory extends Warehouse implements WarehouseInterface{

    /** Creates a new instance of WarehouseFK */
    public WarehouseFKNotMandatory() {
        this.setColumnWidth(40);
        this.setRelatedTable(InventoryWarehouse.class.getName());
        this.setRelatedField("warehouseId");
        this.setMandatory(false);
    }
}
