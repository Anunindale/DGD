/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.inventory.transactions;

/**
 *
 * @author rico
 */
public enum InventorySummaryUpdateOptions {
    ORDERED_IN (0, "ORDERED IN"),
    ORDERED_OUT_RESERVED (1, "ORDERED OUT RESERVED"),//also reserve the stock
    RECEIVED(2,"RECEIVED"),
    DELIVERED(3,"DELIVERED"),
    RETURN(4, "RETURN"),
    ORDERED_OUT(5,"ORDERED OUT"),
    ORDERED_OUT_UNRESERVED(6, "ORDERED OUT UNRESERVED");    //Call with full quantity

    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of InventoryTransactionTypes */
    InventorySummaryUpdateOptions(final int id, final String label) {
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
    
    public static InventorySummaryUpdateOptions fromString(final String str) {
        for (InventorySummaryUpdateOptions e : InventorySummaryUpdateOptions.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static InventorySummaryUpdateOptions fromId(final int id) {
        for (InventorySummaryUpdateOptions e : InventorySummaryUpdateOptions.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
