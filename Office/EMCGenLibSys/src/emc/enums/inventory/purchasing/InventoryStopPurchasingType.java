/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.purchasing;

/**
 *
 * @author rico
 */
public enum InventoryStopPurchasingType {

    YES(0, "Yes"),
    NO(1, "No"),
    OVERSEE(2, "Oversee");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of ItemClassifications */
    InventoryStopPurchasingType(final int id, final String label) {
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

    public static InventoryStopPurchasingType fromString(final String str) {
        for (InventoryStopPurchasingType e : InventoryStopPurchasingType.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static InventoryStopPurchasingType fromId(final int id) {
        for (InventoryStopPurchasingType e : InventoryStopPurchasingType.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
