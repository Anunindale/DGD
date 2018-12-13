/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.purchaseorders;

/**
 *
 * @author riaan
 */
public enum PurchaseOrderInternalRefTypes {

    WO(0, "Works Order"),
    AWO(1, "Aggregate Works Order");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PurchaseOrderInternalRefTypes */
    PurchaseOrderInternalRefTypes(final int id, final String label) {
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

    public static PurchaseOrderInternalRefTypes fromString(final String str) {
        for (PurchaseOrderInternalRefTypes e : PurchaseOrderInternalRefTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PurchaseOrderInternalRefTypes fromId(final int id) {
        for (PurchaseOrderInternalRefTypes e : PurchaseOrderInternalRefTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
