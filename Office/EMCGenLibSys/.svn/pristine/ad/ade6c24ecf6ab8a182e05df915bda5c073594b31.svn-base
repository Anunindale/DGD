/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.pop.purchaseorders;

/**
 *
 * @author wikus
 */
public enum PurchaseOrderMillBoolean {

    YES(0, "YES"),
    NO(1, "NO"),
    UNKNOWN(2, null);
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PurchaseOrderStatus */
    PurchaseOrderMillBoolean(final int id, final String label) {
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

    public static PurchaseOrderMillBoolean fromString(final String str) {
        for (PurchaseOrderMillBoolean e : PurchaseOrderMillBoolean.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PurchaseOrderMillBoolean fromId(final int id) {
        for (PurchaseOrderMillBoolean e : PurchaseOrderMillBoolean.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
