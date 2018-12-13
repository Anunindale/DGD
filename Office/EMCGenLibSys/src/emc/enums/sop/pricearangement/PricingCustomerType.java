/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop.pricearangement;

/**
 *
 * @author rico
 */
public enum PricingCustomerType {

    ALL(0, "All"),
    GROUP(1, "Group"),
    CUSTOMER(1, "Customer");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    PricingCustomerType(final int id, final String label) {
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

    public static PricingCustomerType fromString(final String str) {
        for (PricingCustomerType e : PricingCustomerType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static PricingCustomerType fromId(final int id) {
        for (PricingCustomerType e : PricingCustomerType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
