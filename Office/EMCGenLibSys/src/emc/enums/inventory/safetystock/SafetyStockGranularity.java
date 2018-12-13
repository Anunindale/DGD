/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.safetystock;

/**
 *
 * @author wikus
 */
public enum SafetyStockGranularity {

    WEEK(0, "Week"),
    PERIOD(1, "Period");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of SafetyStockItemType. */
    SafetyStockGranularity(final int id, final String label) {
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

    public static SafetyStockGranularity fromString(final String str) {
        for (SafetyStockGranularity e : SafetyStockGranularity.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SafetyStockGranularity fromId(final int id) {
        for (SafetyStockGranularity e : SafetyStockGranularity.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
