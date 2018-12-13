/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.inventorytables;

/**
 *
 * @author rico
 */
public enum InventoryItemTypes {
    ITEM(0, "Item"),
    BOM(1,"BOM"),
    PHANTOM(2,"Phantom"),
    SERVICE(3,"Service"),
    PLANNING(4, "Planning");
    
    

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryWarehouseTypes */
    InventoryItemTypes(final int id, final String label) {
        this.id = id;
        this.label = label;
    }
    
    public int getId() {
        return id;
    } 
    
    @Override 
    public String toString() {
        return label;
    }
    
    public static InventoryItemTypes fromString(final String str) {
        for (InventoryItemTypes e : InventoryItemTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryItemTypes fromId(final int id) {
        for (InventoryItemTypes e : InventoryItemTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}


