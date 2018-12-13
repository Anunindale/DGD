/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.pop.pricearrangement;

/**
 *
 * @author riaan
 */
public enum PricingSupplierType {

    ALL(0, "All"),
    GROUP(1, "Group"),
    SUPPLIER(2, "Supplier");

    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of PricingSupplierType. */
    PricingSupplierType(final int id, final String label) {
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

    public static PricingSupplierType fromString(final String str) {
        for (PricingSupplierType e : PricingSupplierType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PricingSupplierType fromId(final int id) {
        for (PricingSupplierType e : PricingSupplierType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
