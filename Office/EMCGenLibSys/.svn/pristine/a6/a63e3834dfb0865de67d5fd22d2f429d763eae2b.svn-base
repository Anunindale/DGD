/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.purchaseorders;

/**
 *
 * @author riaan
 */
public enum PurchaseOrderTypes {

    PURCHASE_ORDER(0, "PURCHASE_ORDER"), 
    BLANKET_ORDER(1, "BLANKET_ORDER"); 

    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of PurchaseOrderTypes */
    PurchaseOrderTypes(final int id, final String label) {
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
    
    public static PurchaseOrderTypes fromString(final String str) {
        for (PurchaseOrderTypes e : PurchaseOrderTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static PurchaseOrderTypes fromId(final int id) {
        for (PurchaseOrderTypes e : PurchaseOrderTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
