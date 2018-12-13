/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.posting;

/**
 *
 * @author riaan
 */
public enum DocumentTypes {
    
    PURCHASE_ORDER(0, "PURCHASE_ORDER"),
    RECEIVING_LIST(1,"RECEIVING_LIST"),
    GOODS_RECEIVED_NOTE(2,"GOODS_RECEIVED_NOTE"),
    INVOICE(3, "INVOICE"),
    RELEASE_PO(4, "RELEASE_PO"),
    BLANKET_ORDER(5, "BLANKET_ORDER"),
    RETURN_GOODS(6, "RETURN_GOODS");
    
    
    //Enum variables
    final int id;
    final String label;
    
    /** Creates a new instance of DocumentTypes */
    DocumentTypes(final int id, final String label) {
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
    
    public static DocumentTypes fromString(final String str) {
        for (DocumentTypes e : DocumentTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
    
    public static DocumentTypes fromId(final int id) {
        for (DocumentTypes e : DocumentTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
}
