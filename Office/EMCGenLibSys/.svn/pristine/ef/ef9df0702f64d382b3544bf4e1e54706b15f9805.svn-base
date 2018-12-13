/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.inventorytables;

/**
 *
 * @author wikus
 */
public enum InventoryReferenceTypes {
    PRIMARY(0, "P"),
    CUSTOMER(1,"C"),
    SUPPLIER(2,"S"),
    DEFAULT(3, "D");
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryReferenceTypes */
    InventoryReferenceTypes(final int id, final String label) {
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
    
    public static InventoryReferenceTypes fromString(final String str) {
        for (InventoryReferenceTypes e : InventoryReferenceTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryReferenceTypes fromId(final int id) {
        for (InventoryReferenceTypes e : InventoryReferenceTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}


