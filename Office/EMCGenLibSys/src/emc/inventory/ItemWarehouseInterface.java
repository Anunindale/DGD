/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.inventory;

/**
 * Represents an entity containing an item and a warehouse.
 *
 * @author riaan
 */
public interface ItemWarehouseInterface {

    public String getItemId();

    public void setItemId(String itemId);

    public String getWarehouse();

    public void setWarehouse(String warehouse);
}
