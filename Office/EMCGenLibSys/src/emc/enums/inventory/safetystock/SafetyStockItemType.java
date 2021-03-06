/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.safetystock;

/**
 *
 * @author wikus
 */
public enum SafetyStockItemType {

    ALL(0, "All"),
    GROUP(1, "Group"),
    ITEM(2, "Item");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of SafetyStockItemType. */
    SafetyStockItemType(final int id, final String label) {
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

    public static SafetyStockItemType fromString(final String str) {
        for (SafetyStockItemType e : SafetyStockItemType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SafetyStockItemType fromId(final int id) {
        for (SafetyStockItemType e : SafetyStockItemType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
