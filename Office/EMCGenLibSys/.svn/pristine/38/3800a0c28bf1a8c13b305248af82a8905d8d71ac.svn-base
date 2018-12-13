/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.inventorytables;

/**
 *
 * @author riaan
 */
public enum InventoryItemSubstituteRules {
    
    PERMANENT(0, "Permanent"),
    AUTO(1,"Auto"),
    ASK(2,"Ask"),
    MANUAL(3,"Manual");

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryItemSubstituteRules */
    InventoryItemSubstituteRules(final int id, final String label) {
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
    
    public static InventoryItemSubstituteRules fromString(final String str) {
        for (InventoryItemSubstituteRules e : InventoryItemSubstituteRules.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryItemSubstituteRules fromId(final int id) {
        for (InventoryItemSubstituteRules e : InventoryItemSubstituteRules.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}