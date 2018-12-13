/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.purchaseorders;

/**
 *
 * @author riaan
 */
public enum PurchaseOrderStatus {
    REQUISITION(0, "REQUISITION"), 
    APPROVED(1, "APPROVED"), 
    ORDERED(2, "ORDERED"), 
    PARTIALLY_RECEIVED(3, "PARTIALLY_RECEIVED"), 
    RECEIVED(4, "RECEIVED"), 
    INVOICED(5, "INVOICED"); 
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of PurchaseOrderStatus */
    PurchaseOrderStatus(final int id, final String label) {
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
    
    public static PurchaseOrderStatus fromString(final String str) {
        for (PurchaseOrderStatus e : PurchaseOrderStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static PurchaseOrderStatus fromId(final int id) {
        for (PurchaseOrderStatus e : PurchaseOrderStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
