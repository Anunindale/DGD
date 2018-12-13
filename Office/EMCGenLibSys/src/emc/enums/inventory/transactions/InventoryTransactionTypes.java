/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.transactions;

/**
 *
 * @author riaan
 */
public enum InventoryTransactionTypes {
    PO(0, "PO"),
    JR(1, "JR"),
    WO(2, "WO"),
    MO(3, "MO"),
    SO(4, "SO"),
    INV(5, "INV"),
    CN(6, "CN");

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryTransactionTypes */
    InventoryTransactionTypes(final int id, final String label) {
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
    
    public static InventoryTransactionTypes fromString(final String str) {
        for (InventoryTransactionTypes e : InventoryTransactionTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventoryTransactionTypes fromId(final int id) {
        for (InventoryTransactionTypes e : InventoryTransactionTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
