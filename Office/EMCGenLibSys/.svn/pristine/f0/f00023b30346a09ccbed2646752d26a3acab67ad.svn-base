/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.journals;

/**
 *
 * @author rico
 */
public enum InventoryJournalTypes{
    MOVEMENT(0, "MOVEMENT"),
    STOCKTAKE(0, "STOCKTAKE"),
    TRANSFER(0, "TRANSFER");
    
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of Modules */
    InventoryJournalTypes(final int id, final String label) {
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
    
    public static InventoryJournalTypes fromString(final String str) {
        for (InventoryJournalTypes e : InventoryJournalTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryJournalTypes fromId(final int id) {
        for (InventoryJournalTypes e : InventoryJournalTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}