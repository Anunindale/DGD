/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.transactions;

/**
 *
 * @author riaan
 */
public enum InventoryTransactionDirection {
    IN(0, "IN"),
    OUT(1, "OUT");

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryTransactionDirection */
    InventoryTransactionDirection(final int id, final String label) {
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
    
    public static InventoryTransactionDirection fromString(final String str) {
        for (InventoryTransactionDirection e : InventoryTransactionDirection.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryTransactionDirection fromId(final int id) {
        for (InventoryTransactionDirection e : InventoryTransactionDirection.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}


