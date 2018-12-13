/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.safetystock;

/**
 *
 * @author wikus
 */
public enum SafetyStockCustomerType {

    ALL(0, "All"),
    GROUP(1, "Group"),
    CUSTOMER(2, "Customer");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of SafetyStockCustomerType. */
    SafetyStockCustomerType(final int id, final String label) {
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

    public static SafetyStockCustomerType fromString(final String str) {
        for (SafetyStockCustomerType e : SafetyStockCustomerType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SafetyStockCustomerType fromId(final int id) {
        for (SafetyStockCustomerType e : SafetyStockCustomerType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
