/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.safetystock;

/**
 *
 * @author wikus
 */
public enum SafetyStockType {

    SS(0, "User Generated"),
    MPS(1, "MPS Generated");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of SafetyStockItemType.
     */
    SafetyStockType(final int id, final String label) {
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

    public static SafetyStockType fromString(final String str) {
        for (SafetyStockType e : SafetyStockType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SafetyStockType fromId(final int id) {
        for (SafetyStockType e : SafetyStockType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
