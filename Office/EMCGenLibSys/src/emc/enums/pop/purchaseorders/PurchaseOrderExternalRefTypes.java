/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.purchaseorders;

/**
 *
 * @author riaan
 */
public enum PurchaseOrderExternalRefTypes {

    WO(0, "Works Order"),
    AWO(1, "Aggregate Works Order");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PurchaseOrderExternalRefTypes */
    PurchaseOrderExternalRefTypes(final int id, final String label) {
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

    public static PurchaseOrderExternalRefTypes fromString(final String str) {
        for (PurchaseOrderExternalRefTypes e : PurchaseOrderExternalRefTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PurchaseOrderExternalRefTypes fromId(final int id) {
        for (PurchaseOrderExternalRefTypes e : PurchaseOrderExternalRefTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
