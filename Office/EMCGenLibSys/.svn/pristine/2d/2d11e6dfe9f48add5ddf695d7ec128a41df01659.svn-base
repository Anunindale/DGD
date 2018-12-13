/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.transactions;

/**
 *
 * @author riaan
 */
public enum InventoryTransactionStatus {
    ORDERED(0, "ORDERED"),
    RECEIVED(1,"RECEIVED"),
    SOLD(2,"SOLD"),
    PURCHASED(3,"PURCHASED"),
    RETURNED_GOODS(4, "RETURNED GOODS"),
    DELIVERED(5, "DELIVERED"),
    RESERVED(6, "RESERVED");

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryTransactionStatus */
    InventoryTransactionStatus(final int id, final String label) {
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
    
    public static InventoryTransactionStatus fromString(final String str) {
        for (InventoryTransactionStatus e : InventoryTransactionStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryTransactionStatus fromId(final int id) {
        for (InventoryTransactionStatus e : InventoryTransactionStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

