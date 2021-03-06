/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.inventorytables;

/**
 *
 * @author rico
 */
public enum InventoryOrderMethods {
    MIN_MAX(0, "Min_Max");
    
    

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryWarehouseTypes */
    InventoryOrderMethods(final int id, final String label) {
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
    
    public static InventoryOrderMethods fromString(final String str) {
        for (InventoryOrderMethods e : InventoryOrderMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryOrderMethods fromId(final int id) {
        for (InventoryOrderMethods e : InventoryOrderMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}


