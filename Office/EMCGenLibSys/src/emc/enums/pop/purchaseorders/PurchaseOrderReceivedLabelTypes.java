/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.purchaseorders;

/**
 *
 * @author riaan
 */
public enum PurchaseOrderReceivedLabelTypes {
    GRN(0, "Goods Received Labels"),
    CRATE(1, "Crate Labels"),
    FG_BOX(2, "Finished Goods Box Labels");
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of PurchaseOrderStatus */
    PurchaseOrderReceivedLabelTypes(final int id, final String label) {
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
    
    public static PurchaseOrderReceivedLabelTypes fromString(final String str) {
        for (PurchaseOrderReceivedLabelTypes e : PurchaseOrderReceivedLabelTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static PurchaseOrderReceivedLabelTypes fromId(final int id) {
        for (PurchaseOrderReceivedLabelTypes e : PurchaseOrderReceivedLabelTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
