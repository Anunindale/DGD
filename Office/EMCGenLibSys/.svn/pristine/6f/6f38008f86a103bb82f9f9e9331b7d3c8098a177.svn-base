/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.purchaseorders;

/**
 *
 * @author riaan
 */
public enum PurchaseOrderCancelledStatus {

    PARTIALLY_CANCELLED(0, "P"),
    CANCELLED(1, "C");

    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of PurchaseOrderCancelledStatus */
    PurchaseOrderCancelledStatus(final int id, final String label) {
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
    
    public static PurchaseOrderCancelledStatus fromString(final String str) {
        for (PurchaseOrderCancelledStatus e : PurchaseOrderCancelledStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static PurchaseOrderCancelledStatus fromId(final int id) {
        for (PurchaseOrderCancelledStatus e : PurchaseOrderCancelledStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
